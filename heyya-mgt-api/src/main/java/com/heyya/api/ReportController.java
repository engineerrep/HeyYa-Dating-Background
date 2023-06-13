package com.heyya.api;

import com.github.pagehelper.PageInfo;
import com.heyya.model.dto.ReportSearchDto;
import com.heyya.model.dto.ReportUpdateDto;
import com.heyya.model.resp.Response;
import com.heyya.model.vo.ReportVo;
import com.heyya.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@Api(tags = "举报 API")
@RequestMapping("/v1/report")
public class ReportController extends BaseController {
    @Autowired
    private ReportService reportService;

    @ApiOperation(value = "举报列表 API")
    @GetMapping
    public Response<PageInfo<ReportVo>> spark(@Valid ReportSearchDto dto) {
        return Response.success(reportService.page(dto));
    }

    @ApiOperation(value = "修改 API")
    @PutMapping
    public Response update(@RequestBody @Valid List<ReportUpdateDto> dto) {
        reportService.update(dto);
        return Response.success();
    }

}
