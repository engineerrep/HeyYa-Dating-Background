package com.heyya.api;

import com.heyya.config.auth.UserAuthContext;
import com.heyya.model.dto.FeedbackPersistDto;
import com.heyya.model.resp.Response;
import com.heyya.service.FeedbackService;
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
@RequestMapping("/v1/feedback")
@Api(tags = "Feedback API")
public class FeedbackController extends BaseController {
    @Autowired
    private FeedbackService feedbackService;

    @ApiOperation("新增")
    @PostMapping
    public Response<Object> post(@RequestBody @Valid FeedbackPersistDto persist) {
        persist.setUserId(UserAuthContext.getId());
        feedbackService.save(persist);
        return Response.SUCCESS;
    }

}
