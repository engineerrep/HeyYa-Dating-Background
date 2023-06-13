package com.heyya.api;

import com.github.pagehelper.PageInfo;
import com.heyya.model.dto.MomentSearchDto;
import com.heyya.model.dto.MomentUpdateDto;
import com.heyya.model.resp.Response;
import com.heyya.model.vo.MomentVo;
import com.heyya.service.MomentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@Api(tags = "Moment API")
@RequestMapping("/v1/moment")
public class MomentController extends BaseController {

    @Autowired
    private MomentService momentService;


    @ApiOperation("Moment审核列表")
    @GetMapping
    public Response<PageInfo<MomentVo>> search(@Valid MomentSearchDto dto) {
        return Response.success(momentService.page(dto));
    }

    @ApiOperation("moment审核")
    @PutMapping("/audit")
    public Response<Object> audit(@RequestBody List<MomentUpdateDto> audit) {
        momentService.audit(audit);
        return Response.success();
    }

}
