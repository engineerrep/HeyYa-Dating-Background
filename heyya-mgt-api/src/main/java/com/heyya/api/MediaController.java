package com.heyya.api;

import com.github.pagehelper.PageInfo;
import com.heyya.model.dto.MediaUpdateDto;
import com.heyya.model.dto.MomentSearchDto;
import com.heyya.model.resp.Response;
import com.heyya.model.vo.MediaVo;
import com.heyya.service.MediaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@Api(tags = "媒体资源 API")
@RequestMapping("/v1/media")
public class MediaController extends BaseController {

    @Autowired
    private MediaService mediaService;

    @ApiOperation("Media 审核列表")
    @GetMapping
    public Response<PageInfo<MediaVo>> search(@Valid MomentSearchDto dto) {
        return Response.success(mediaService.page(dto));
    }

    @ApiOperation("Media 审核")
    @PutMapping("/audit")
    public Response<Object> audit(@RequestBody List<MediaUpdateDto> audit) {
        mediaService.audit(audit);
        return Response.SUCCESS;
    }
}
