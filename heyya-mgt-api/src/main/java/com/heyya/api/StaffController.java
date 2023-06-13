package com.heyya.api;

import com.heyya.code.CodeResponse;
import com.heyya.model.dto.StaffSigninDto;
import com.heyya.model.resp.Response;
import com.heyya.model.vo.StaffSigninVo;
import com.heyya.service.StaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Validated
@RestController
@RequestMapping("/v1/staff")
@Api(tags = "员工 API")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @ApiOperation("员工登录")
    @PostMapping("/sign-in")
    public Response<StaffSigninVo> signin(@RequestBody StaffSigninDto dto) {
        StaffSigninVo signin = staffService.signin(dto);
        if (Objects.nonNull(signin)) {
            return Response.success(signin);
        }
        return CodeResponse.LOGIN_FAILED;
    }

}
