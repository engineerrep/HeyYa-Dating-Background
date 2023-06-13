package com.heyya.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heyya.constans.ImKeyConts;
import com.heyya.mapper.AccountMapper;
import com.heyya.model.converter.AccountConverter;
import com.heyya.model.converter.UserConverter;
import com.heyya.model.dto.*;
import com.heyya.model.entity.Account;
import com.heyya.model.entity.User;
import com.heyya.model.enums.Active;
import com.heyya.model.enums.ImSendMsgEnum;
import com.heyya.model.enums.SignType;
import com.heyya.model.vo.SingInVo;
import com.heyya.tencent.service.ImService;
import com.heyya.tools.oauth.apple.AppleAuthResult;
import com.heyya.tools.oauth.apple.AppleAuthVerifier;
import com.heyya.tools.oauth.google.GoogleAuthResult;
import com.heyya.tools.oauth.google.GoogleAuthVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
public class AccountService extends ServiceImpl<AccountMapper, Account> {

    @Autowired
    private AccountConverter accountConverter;
    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private ImService imService;

    @Transactional(rollbackFor = Exception.class)
    public Account save(AccountPersistDto persistDto) {
        Account account = accountConverter.dto2Entity(persistDto);
        if (save(account)) {
            return getById(account.getId());
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public SingInVo singIn(SingInDto dto) {
        String account;
        switch (dto.getType()) {
            case APPLEID:
                AppleAuthResult appleResult = AppleAuthVerifier.verify(dto.getAccount(), dto.getBundleId());
                account = appleResult.getSub();
                break;
            case GOOGLE:
                GoogleAuthResult googleResult = GoogleAuthVerifier.verify(dto.getAccount());
                account = googleResult.getSub();
                break;
            case DEVICE:
                account = dto.getAccount();
                break;
            default:
                return null;
        }
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        Account acc = getOne(queryWrapper);
        if (Objects.isNull(acc)) {
            User user = userService.save(userConverter.dto2dto(dto));
            acc = new Account();
            acc.setUserId(user.getId());
            acc.setAccount(account);
            acc.setType(dto.getType());
            save(acc);
            BaseIMSendMsgDto imSendMsgDTO = new BaseIMSendMsgDto();
            imSendMsgDTO.setContent(new IMAccountImportDto(ImKeyConts.IM_KEY + user.getId(), user.getNickname(), null));
            imSendMsgDTO.setImSendMsgEnum(ImSendMsgEnum.ACCOUNT_IMPORT);
            imService.imSendMsg(imSendMsgDTO);
            SingInVo singInVo = accountConverter.entity2SingInVo(getById(acc.getId()));
            singInVo.setSignType(SignType.SIGNUP);
            return singInVo;
        }
        SingInVo singInVo = accountConverter.entity2SingInVo(acc);
        singInVo.setSignType(SignType.SIGNIN);
        return singInVo;
    }

    @Transactional(rollbackFor = Exception.class)
    public Account update(AccountUpdateDto updateDto) {
        Account account = accountConverter.dto2Entity(updateDto);
        if (updateById(account)) {
            return getById(account.getId());
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean delAccount(Long userId) {
        LambdaQueryWrapper<Account> queryWrapper = new LambdaQueryWrapper<Account>()
                .eq(Account::getUserId, userId);
        Account account = getOne(queryWrapper);
        if (Objects.nonNull(account)) {
            UserUpdateDto userUpdate = new UserUpdateDto();
            userUpdate.setId(account.getUserId());
            userUpdate.setActive(Active.DELETED);
            userService.update(userUpdate);
            return removeById(account.getId());
        }
        return Boolean.FALSE;
    }

}
