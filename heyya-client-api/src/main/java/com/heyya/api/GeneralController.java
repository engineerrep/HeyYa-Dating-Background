package com.heyya.api;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.heyya.code.CodeResponse;
import com.heyya.config.auth.UserAuthContext;
import com.heyya.model.converter.UserConverter;
import com.heyya.model.dto.*;
import com.heyya.model.entity.User;
import com.heyya.model.enums.MediaType;
import com.heyya.model.enums.VerifyState;
import com.heyya.model.resp.Response;
import com.heyya.model.vo.*;
import com.heyya.service.*;
import com.heyya.tools.utils.S3Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Validated
@RestController
@RequestMapping("/v1")
@Api(tags = "通用 API")
public class GeneralController extends BaseController {

    @Autowired
    private S3Utils s3Utils;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private VisitorService visitorService;
    @Autowired
    private UnLikeService unLikeService;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private SubscribeEmailService emailService;

    @ApiOperation("上传文件,返回文件路径")
    @PostMapping("/upload")
    public Response<String> upload1(@RequestParam("file") MultipartFile file) {
        if (file.getSize() == 0L) {
            return CodeResponse.FILE_NOT_EMPTY;
        }
        if (StringUtils.isBlank(file.getOriginalFilename())) {
            return CodeResponse.FILE_UPLOAD_FAIL;
        }
        return Response.success(s3Utils.uploadByFile(file));
    }

    @ApiOperation(value = "批量上传文件,返回文件路径")
    @PostMapping("/batch-upload")
    public Response<List<String>> batchUpload(@RequestParam("files") List<MultipartFile> files) {
        if (CollectionUtils.isEmpty(files)) {
            return CodeResponse.FILE_NOT_EMPTY;
        }
        return Response.success(s3Utils.batchUpload(files));
    }

    @ApiOperation(value = "登录/注册")
    @PostMapping("/sing-in")
    public Response<SingInVo> singIn(@Valid @RequestBody SingInDto dto) {
        SingInVo singInVo = accountService.singIn(dto);
        if (Objects.nonNull(singInVo)) {
            UserVo userVo = userConverter.entity2Vo(userService.getOne(Long.valueOf(singInVo.getUser().getId())));
            userVo.setMedias(mediaService.listVo(Long.valueOf(userVo.getId())));
            singInVo.setUser(userVo);
        }
        return Response.success(singInVo);
    }

    @ApiOperation(value = "SPARK API")
    @GetMapping
    public Response<PageInfo<SparkVo>> spark(@Valid SparkSearchDto dto) {
        User user = userService.getById(UserAuthContext.getId());
        if (Objects.nonNull(user) && StringUtils.isNotEmpty(user.getSex())) {
            dto.setSex(user.getSex().equals("FEMALE") ? "MALE" : "FEMALE");
        } else {
            dto.setSex("FEMALE");
        }
        return Response.success(userService.spark(dto));
    }

    @ApiOperation("查询我的关系数量")
    @GetMapping("/rel-num")
    public Response<RelNumberVO> relNum() {
        return Response.success(visitorService.rel());
    }

    @ApiOperation("unlike")
    @PostMapping("/unlike")
    public Response<Object> unlike(@RequestBody @Valid UnLikePersistDto dto) {
        unLikeService.save(dto);
        return Response.SUCCESS;
    }

    @ApiOperation("删除账号API")
    @DeleteMapping("/del-account")
    public Response<Object> delAccount() {
        if (accountService.delAccount(UserAuthContext.getId())) {
            return Response.SUCCESS;
        }
        return CodeResponse.DEL_ACCOUNT;
    }

    @ApiOperation("Video 查询列表")
    @GetMapping("/video/search")
    public Response<PageInfo<SparkVideoVo>> videoSearch(@Valid MediaSearchDto searchDto) {
        searchDto.setMediaTypes(Lists.newArrayList(MediaType.VERIFY_VIDEO, MediaType.MAIN_VIDEO, MediaType.VIDEO));
        searchDto.setVerifyState(VerifyState.CHECKED);
        return Response.success(mediaService.search(searchDto));
    }

    @ApiOperation("预约用户添加Email")
    @PostMapping("/sub/email")
    public Response subscribeEmail(@Valid SubscribeEmailDto dto) {
        emailService.save(dto.getEmail());
        return Response.success();
    }

}