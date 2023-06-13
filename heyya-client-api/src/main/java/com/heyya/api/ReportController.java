package com.heyya.api;

import com.heyya.model.dto.ReportPersistDto;
import com.heyya.model.resp.Response;
import com.heyya.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@Api(tags = "举报 API")
@RequestMapping("/v1/report")
public class ReportController extends BaseController {
    @Autowired
    private ReportService reportService;

    @ApiOperation("新增")
    @PostMapping
    public Response<Object> post(@RequestBody @Valid ReportPersistDto persist) {
        reportService.save(persist);
        return Response.SUCCESS;
    }

}
