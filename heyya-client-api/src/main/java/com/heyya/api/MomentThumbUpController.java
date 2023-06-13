package com.heyya.api;

import com.heyya.config.auth.UserAuthContext;
import com.heyya.model.dto.MomentThumbUpPersistDto;
import com.heyya.model.resp.Response;
import com.heyya.service.MomentThumbUpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Moment点赞 API")
@RequestMapping(value = "/v1/thumb-ups")
@RestController
public class MomentThumbUpController {

    @Autowired
    private MomentThumbUpService thumbUpService;

    @ApiOperation(value = "添加点赞")
    @PostMapping
    public Response<Object> save(@RequestBody @Valid MomentThumbUpPersistDto dto) {
        dto.setUserId(UserAuthContext.getId());
        thumbUpService.save(dto);
        return Response.SUCCESS;
    }

    @ApiOperation(value = "取消点赞")
    @DeleteMapping("/{moment-id}")
    public Response<Object> cancelThumbUp(@PathVariable(name = "moment-id") Long MomentId) {
        thumbUpService.del(MomentId);
        return Response.SUCCESS;
    }

}
