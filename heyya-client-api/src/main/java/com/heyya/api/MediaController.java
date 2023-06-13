package com.heyya.api;

import com.github.pagehelper.PageInfo;
import com.heyya.model.dto.MediaPersistDto;
import com.heyya.model.dto.MediaPrivacyDto;
import com.heyya.model.dto.MediaSearchDto;
import com.heyya.model.dto.MediaUpdateDto;
import com.heyya.model.enums.VerifyState;
import com.heyya.model.resp.Response;
import com.heyya.model.vo.MediaVo;
import com.heyya.service.MediaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liquibase.util.StringUtils;
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

    @ApiOperation("新增")
    @PostMapping("/save")
    public Response<MediaVo> save(@RequestBody MediaPersistDto dto) {
        return Response.success(mediaService.save(dto));
    }

    @ApiOperation("批量新增")
    @PostMapping("/save-all")
    public Response saveAll(@RequestBody List<MediaPersistDto> dtos) {
        mediaService.saveAll(dtos);
        return Response.success();
    }

    @GetMapping("/list")
    @ApiOperation("媒体资源列表")
    public PageInfo<MediaVo> list(MediaSearchDto dto) {
        return mediaService.findByResourceId(dto);
    }

    @PutMapping
    @ApiOperation("修改媒体信息")
    public Response<MediaVo> update(@RequestBody MediaUpdateDto dto) {
        if (StringUtils.isNotEmpty(dto.getUrl()) || StringUtils.isNotEmpty(dto.getContent())) {
            dto.setVerifyState(VerifyState.UNCHECKED);
        }
        return Response.success(mediaService.update(dto));
    }

    @ApiOperation("删除视频")
    @DeleteMapping("/{id}")
    public Response del(@PathVariable(value = "id") @Valid Long id) {
        mediaService.removeById(id);
        return Response.SUCCESS;
    }

    @ApiOperation("设置主视频")
    @PutMapping("/{id}")
    public Response putMainVideo(@PathVariable(value = "id") Long id) {
        mediaService.putMainVideo(id);
        return Response.SUCCESS;
    }

    @ApiOperation("设置视频隐私状态")
    @PutMapping("/privacy")
    public Response<MediaVo> putPrivacy(@Valid @RequestBody MediaPrivacyDto dto) {
        return Response.success(mediaService.privacy(dto));
    }
}
