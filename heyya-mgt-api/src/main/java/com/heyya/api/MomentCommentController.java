package com.heyya.api;

import com.github.pagehelper.PageInfo;
import com.heyya.model.dto.MomentCommentSearchDto;
import com.heyya.model.resp.Response;
import com.heyya.model.vo.MomentCommentVo;
import com.heyya.service.MomentCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@Api(tags = "MomentComment API")
@RequestMapping("/v1/moment-comment")
public class MomentCommentController extends BaseController {

    @Autowired
    private MomentCommentService commentService;

    @ApiOperation("momentComment 审核列表")
    @GetMapping
    public Response<PageInfo<MomentCommentVo>> search(@Valid MomentCommentSearchDto dto) {
        return Response.success(commentService.search(dto));
    }

    @ApiOperation("momentComment 审核/删除")
    @DeleteMapping
    public Response<Object> del(@RequestParam List<Long> ids) {
        commentService.removeBatchByIds(ids);
        return Response.SUCCESS;
    }
}
