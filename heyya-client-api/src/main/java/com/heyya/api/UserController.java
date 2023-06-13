package com.heyya.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heyya.code.CodeResponse;
import com.heyya.config.auth.UserAuthContext;
import com.heyya.model.converter.MediaConverter;
import com.heyya.model.converter.UserConverter;
import com.heyya.model.dto.UserUpdateDto;
import com.heyya.model.dto.VisitorPersistDto;
import com.heyya.model.entity.Friend;
import com.heyya.model.entity.Media;
import com.heyya.model.entity.UnLike;
import com.heyya.model.entity.User;
import com.heyya.model.enums.FriendRelationType;
import com.heyya.model.enums.VerifyState;
import com.heyya.model.resp.Response;
import com.heyya.model.vo.MyProfileVo;
import com.heyya.model.vo.UserVo;
import com.heyya.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

@Validated
@RestController
@Api(tags = "用户 API")
@RequestMapping("/v1/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Resource
    private UserConverter userConverter;
    @Autowired
    private MediaService mediaService;
    @Resource
    private MediaConverter mediaConverter;
    @Autowired
    private VisitorService visitorService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private UnLikeService unLikeService;

    @ApiOperation("修改Profile")
    @PutMapping
    public Response<Object> put(@RequestBody UserUpdateDto updateDto) {
        updateDto.setId(UserAuthContext.getId());
        if (StringUtils.isNotBlank(updateDto.getAboutMe())) {
            updateDto.setAboutMeState(VerifyState.UNCHECKED);
        }
        if (StringUtils.isNotBlank(updateDto.getAvatar())) {
            updateDto.setAvatarState(VerifyState.UNCHECKED);
        }
        if (StringUtils.isNotBlank(updateDto.getNickname())) {
            updateDto.setNicknameState(VerifyState.UNCHECKED);
        }
        User user = userService.update(updateDto);
        if (Optional.of(user).isPresent()) {
            UserVo userVo = userService.getUserVo(String.valueOf(user.getId()));
            userVo.setMedias(mediaService.list(user.getId()));
            return Response.success(userVo);
        }
        return CodeResponse.FAILED;
    }

    @ApiOperation("查看他人profile")
    @GetMapping("/{id}")
    public Response<UserVo> profile(@PathVariable("id") Long userId) {
        UserVo user = userConverter.entity2Vo(userService.getOne(userId));
        if (Objects.nonNull(user)) {
            user.setMedias(mediaService.list(userId));
            if (!UserAuthContext.getId().equals(userId)) {
                VisitorPersistDto visitor = new VisitorPersistDto();
                visitor.setFromUserId(UserAuthContext.getId());
                visitor.setToUserId(userId);
                visitorService.save(visitor);
            }
            LambdaQueryWrapper<Friend> myLike = new LambdaQueryWrapper<Friend>()
                    .eq(Friend::getFromUserId, UserAuthContext.getId())
                    .eq(Friend::getToUserId, userId)
                    .eq(Friend::getRelation, FriendRelationType.IN_MY_FRIEND_LIST);
            user.setLiked(friendService.count(myLike) > 0);
            LambdaQueryWrapper<Friend> match = new LambdaQueryWrapper<Friend>()
                    .eq(Friend::getFromUserId, UserAuthContext.getId())
                    .eq(Friend::getToUserId, userId)
                    .eq(Friend::getRelation, FriendRelationType.BOTH_WAY);
            user.setMatchd(friendService.count(match) > 0);
            LambdaQueryWrapper<UnLike> unLikeQuery = new LambdaQueryWrapper<UnLike>()
                    .eq(UnLike::getFromUserId, UserAuthContext.getId())
                    .eq(UnLike::getToUserId, userId);
            user.setPassed(unLikeService.count(unLikeQuery) > 0);
        }
        return Response.success(user);
    }

    @ApiOperation("查看me profile")
    @GetMapping
    public Response<MyProfileVo> meProfile() {
        MyProfileVo profileVo = userConverter.entity2myProfileVo(userService.getOne(UserAuthContext.getId()));
        if (Objects.nonNull(profileVo)) {
            LambdaQueryWrapper<Media> queryWrapper = new LambdaQueryWrapper<Media>()
                    .eq(Media::getResourceId, UserAuthContext.getId());
            profileVo.setMedias(mediaConverter.entity2Vo(mediaService.list(queryWrapper)));
        }
        return Response.success(profileVo);
    }

}
