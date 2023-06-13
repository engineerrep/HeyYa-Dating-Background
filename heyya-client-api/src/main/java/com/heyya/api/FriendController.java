package com.heyya.api;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.heyya.config.auth.UserAuthContext;
import com.heyya.model.dto.FriendPersistDto;
import com.heyya.model.dto.FriendSearchDto;
import com.heyya.model.enums.FriendRelationType;
import com.heyya.model.resp.Response;
import com.heyya.model.vo.FriendVo;
import com.heyya.service.BlockService;
import com.heyya.service.FriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/v1/friend")
@Api(tags = "好友 API")
public class FriendController extends BaseController {

    @Autowired
    private FriendService friendService;
    @Autowired
    private BlockService blockService;

    @ApiOperation("新增")
    @PostMapping("/save")
    public Response<FriendVo> save(@RequestBody FriendPersistDto dto) {
        dto.setFromUserId(UserAuthContext.getId());
        return Response.success(friendService.save(dto));
    }

    @ApiOperation("我的Like列表")
    @GetMapping("/me-like")
    public Response<PageInfo<FriendVo>> meLike(@Valid FriendSearchDto dto) {
        dto.setFromUserId(UserAuthContext.getId());
        dto.setRelationOR(Lists.newArrayList(FriendRelationType.IN_MY_FRIEND_LIST));
        dto.setBlockIds(blockService.blockIds());
        return Response.success(friendService.search(dto));
    }

    @ApiOperation("likeMe我的列表")
    @GetMapping("/like-me")
    public Response<PageInfo<FriendVo>> likeMe(@Valid FriendSearchDto dto) {
        dto.setFromUserId(UserAuthContext.getId());
        dto.setRelationOR(Lists.newArrayList(FriendRelationType.IN_OTHER_FRIEND_LIST));
        dto.setBlockIds(blockService.blockIds());
        return Response.success(friendService.search(dto));
    }

    @ApiOperation("match列表")
    @GetMapping("/match")
    public Response<PageInfo<FriendVo>> match(@Valid FriendSearchDto dto) {
        dto.setFromUserId(UserAuthContext.getId());
        dto.setRelationOR(Lists.newArrayList(FriendRelationType.BOTH_WAY));
        dto.setBlockIds(blockService.blockIds());
        return Response.success(friendService.search(dto));
    }

}
