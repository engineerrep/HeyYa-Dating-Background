package com.heyya.api;

import com.github.pagehelper.PageInfo;
import com.heyya.config.auth.UserAuthContext;
import com.heyya.model.converter.MomentCommentConverter;
import com.heyya.model.dto.MomentCommentPersistDto;
import com.heyya.model.dto.MomentCommentSearchDto;
import com.heyya.model.resp.Response;
import com.heyya.model.vo.MomentCommentVo;
import com.heyya.service.MomentCommentService;
import com.heyya.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/v1/comment")
@Api(tags = "Moment评论表 API")
public class MomentCommentController extends BaseController {
    @Autowired
    private MomentCommentService commentService;
    @Autowired
    private MomentCommentConverter commentConverter;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "发布moment评论")
    @PostMapping
    public Response<MomentCommentVo> save(@RequestBody @Valid MomentCommentPersistDto dto) {
        dto.setUserId(UserAuthContext.getId());
        MomentCommentVo commentVo = commentConverter.entity2Vo(commentService.save(dto));
        if (Objects.nonNull(commentVo)) {
            commentVo.setUser(userService.getUserVo(commentVo.getUserId()));
        }
        return Response.success(commentVo);
    }

    @ApiOperation("moment评论列表")
    @GetMapping("/search")
    public Response<PageInfo<MomentCommentVo>> search(@Valid MomentCommentSearchDto dto) {
        return Response.success(commentService.search(dto));
    }
}
