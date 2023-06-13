package com.heyya.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heyya.code.CodeResponse;
import com.heyya.config.auth.UserToken;
import com.heyya.config.auth.UserTokenUtils;
import com.heyya.exception.SvcException;
import com.heyya.mapper.StaffMapper;
import com.heyya.model.converter.StaffConverter;
import com.heyya.model.dto.StaffPersistDto;
import com.heyya.model.dto.StaffSigninDto;
import com.heyya.model.entity.Staff;
import com.heyya.model.enums.Active;
import com.heyya.model.enums.Deleted;
import com.heyya.model.vo.StaffSigninVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class StaffService extends ServiceImpl<StaffMapper, Staff> {

    @Resource
    private StaffConverter staffConverter;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public void save(StaffPersistDto dto) {
        if (Objects.nonNull(findByAccount(dto.getAccount()))) {
            throw new SvcException(CodeResponse.ACCOUNT_EXISTS);
        }
        Staff staff = staffConverter.dto2Entity(dto);
        staff.setPassword(passwordEncoder.encode(staff.getPassword()));
        staff.setState(Active.ACTIVE);
        save(staff);
    }

    public Staff findByAccount(String account) {
        QueryWrapper<Staff> queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", account);
        queryWrapper.eq("deleted", Deleted.NO);
        return getOne(queryWrapper);
    }

    public StaffSigninVo signin(StaffSigninDto dto) {
        Staff staff = findByAccount(dto.getAccount());
        if (Objects.isNull(staff) || !passwordEncoder.matches(dto.getPassword(), staff.getPassword())) {
            return null;
        }
        return new StaffSigninVo(UserTokenUtils.create(new UserToken(staff.getId())));
    }

}
