package com.heyya.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heyya.code.CodeResponse;
import com.heyya.config.auth.UserAuthContext;
import com.heyya.constans.ImKeyConts;
import com.heyya.exception.SvcException;
import com.heyya.mapper.BlockMapper;
import com.heyya.model.converter.BlockConverter;
import com.heyya.model.dto.BaseIMSendMsgDto;
import com.heyya.model.dto.BlockPersistDto;
import com.heyya.model.dto.BlockSearchDto;
import com.heyya.model.dto.IMMsgDto;
import com.heyya.model.entity.Block;
import com.heyya.model.entity.Friend;
import com.heyya.model.enums.ImSendMsgEnum;
import com.heyya.model.vo.BlockVo;
import com.heyya.tencent.service.ImService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BlockService extends ServiceImpl<BlockMapper, Block> {

    @Resource
    private BlockConverter blockConverter;
    @Autowired
    private FriendService friendService;
    @Autowired
    private ImService imService;
    @Autowired
    private UserService userService;

    @Transactional(rollbackFor = Exception.class)
    public void save(BlockPersistDto dto) {
        LambdaQueryWrapper<Block> queryWrapper = new QueryWrapper<Block>().lambda()
                .eq(Block::getFromUserId, dto.getFromUserId())
                .eq(Block::getToUserId, dto.getToUserId());
        Block block = getOne(queryWrapper);
        if (Objects.nonNull(block)) {
            throw new SvcException(CodeResponse.BLACKENED);
        }
        if (save(blockConverter.dto2Entity(dto))) {
            LambdaQueryWrapper<Friend> friendQuery = new QueryWrapper<Friend>().lambda()
                    .eq(Friend::getFromUserId, dto.getFromUserId())
                    .eq(Friend::getToUserId, dto.getToUserId());
            friendService.remove(friendQuery);

            LambdaQueryWrapper<Friend> toQuery = new QueryWrapper<Friend>().lambda()
                    .eq(Friend::getFromUserId, dto.getToUserId())
                    .eq(Friend::getToUserId, dto.getFromUserId());
            friendService.remove(toQuery);

            BaseIMSendMsgDto<IMMsgDto> imSendMsgDTO = new BaseIMSendMsgDto<>();
            imSendMsgDTO.setImSendMsgEnum(ImSendMsgEnum.ADD_BLOCK);
            IMMsgDto im = new IMMsgDto();
            im.setBeBlockId(ImKeyConts.IM_KEY + dto.getToUserId());
            im.setUserId(ImKeyConts.IM_KEY + dto.getFromUserId());
            imSendMsgDTO.setContent(im);
            imService.imSendMsg(imSendMsgDTO);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancelBlack(Long toUserId) {
        LambdaQueryWrapper<Block> queryWrapper = new QueryWrapper<Block>().lambda()
                .eq(Block::getFromUserId, UserAuthContext.getId())
                .eq(Block::getToUserId, toUserId);
        if (remove(queryWrapper)) {
            BaseIMSendMsgDto<IMMsgDto> imSendMsgDTO = new BaseIMSendMsgDto<>();
            imSendMsgDTO.setImSendMsgEnum(ImSendMsgEnum.DELETE_BLOCK);
            IMMsgDto im = new IMMsgDto();
            im.setBeBlockId(ImKeyConts.IM_KEY + toUserId);
            im.setUserId(ImKeyConts.IM_KEY + UserAuthContext.getId());
            imSendMsgDTO.setContent(im);
            imService.imSendMsg(imSendMsgDTO);
        }
    }

    public Set<Long> blockIds() {
        LambdaQueryWrapper<Block> queryWrapper = new QueryWrapper<Block>().lambda()
                .eq(Block::getFromUserId, UserAuthContext.getId());
        return list(queryWrapper).stream().map(Block::getToUserId).collect(Collectors.toSet());
    }

    public PageInfo<BlockVo> search(BlockSearchDto dto) {
        LambdaQueryWrapper<Block> queryWrapper = new LambdaQueryWrapper<Block>()
                .eq(Objects.nonNull(dto.getUserId()), Block::getFromUserId, dto.getUserId())
                .eq(Objects.nonNull(dto.getToUserId()), Block::getToUserId, dto.getToUserId());
        PageHelper.startPage(dto.getNumber(), dto.getSize());
        PageInfo<BlockVo> pageInfo = blockConverter.pageEntity2PageVo(new PageInfo<>(list(queryWrapper)));
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            pageInfo.getList().forEach(i -> {
                i.setToUser(userService.getUserVo(i.getToUser().getId()));
            });
        }
        return pageInfo;
    }

}
