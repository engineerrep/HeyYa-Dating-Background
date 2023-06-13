package com.heyya.api;

import com.github.pagehelper.PageInfo;
import com.heyya.config.auth.UserAuthContext;
import com.heyya.model.dto.VisitorSearchDto;
import com.heyya.model.resp.Response;
import com.heyya.model.vo.VisitorVo;
import com.heyya.service.VisitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@Api(tags = "访客 API")
@RequestMapping("/v1/visitor")
public class VisitorController extends BaseController {

    @Autowired
    private VisitorService visitorService;

    @ApiOperation("Visitor列表")
    @GetMapping("/search")
    public Response<PageInfo<VisitorVo>> search(@Valid VisitorSearchDto dto) {
        dto.setToUserId(UserAuthContext.getId());
        return Response.success(visitorService.search(dto));
    }
}
