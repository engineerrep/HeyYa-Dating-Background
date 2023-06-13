package com.heyya.api;

import com.github.pagehelper.PageInfo;
import com.heyya.code.CodeResponse;
import com.heyya.model.dto.ProfileAuditDto;
import com.heyya.model.dto.UserSearchDto;
import com.heyya.model.dto.UserUpdateDto;
import com.heyya.model.entity.User;
import com.heyya.model.resp.Response;
import com.heyya.model.vo.UserVo;
import com.heyya.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@Api(tags = "用户 API")
@RequestMapping("/v1/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户列表 API")
    @GetMapping
    public Response<PageInfo<UserVo>> page(@Valid UserSearchDto dto) {
        return Response.success(userService.page(dto));
    }

    @ApiOperation("冻结、解冻")
    @PutMapping
    public Response<Object> put(@RequestBody UserUpdateDto updateDto) {
        User user = userService.update(updateDto);
        if (Optional.of(user).isPresent()) {
            return Response.success(userService.getUserVo(String.valueOf(user.getId())));
        }
        return CodeResponse.FAILED;
    }

    @ApiOperation("用户审核")
    @PutMapping("/audit")
    public Response<Object> audit(@RequestBody List<ProfileAuditDto> audit) {
        userService.audit(audit);
        return Response.success();
    }
}
