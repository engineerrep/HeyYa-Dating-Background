package com.heyya.api;

import com.github.pagehelper.PageInfo;
import com.heyya.config.auth.UserAuthContext;
import com.heyya.model.converter.MomentConverter;
import com.heyya.model.dto.MomentPersistDto;
import com.heyya.model.dto.MomentSearchDto;
import com.heyya.model.resp.Response;
import com.heyya.model.vo.MomentVo;
import com.heyya.service.MediaService;
import com.heyya.service.MomentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

@Validated
@RestController
@Api(tags = "Moment API")
@RequestMapping("/v1/moment")
public class MomentController extends BaseController {

    @Autowired
    private MomentService momentService;
    @Resource
    private MomentConverter momentConverter;
    @Autowired
    private MediaService mediaService;

    @ApiOperation("新增")
    @PostMapping("/save")
    public Response save(@Valid @RequestBody MomentPersistDto dto) {
        dto.setUserId(UserAuthContext.getId());
        return Response.success(momentService.save(dto));
    }

    @ApiOperation("查看Moment详情")
    @GetMapping("/{id}")
    public Response<MomentVo> get(@Valid @PathVariable("id") Long id) {
        MomentVo momentVo = momentConverter.entity2Vo(momentService.getOne(id));
        if (Objects.nonNull(momentVo)) {
            momentVo.setMedias(mediaService.listVo(Long.valueOf(momentVo.getId())));
        }
        return Response.success(momentVo);
    }

    @ApiOperation("Moment列表")
    @GetMapping
    public Response<PageInfo<MomentVo>> search(@Valid MomentSearchDto dto) {
        return Response.success(momentService.search(dto));
    }

    @ApiModelProperty("删除moment")
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable(value = "id") Long id) {
        momentService.removeById(id);
        return Response.SUCCESS;
    }
}
