package com.heyya.api;

import com.github.pagehelper.PageInfo;
import com.heyya.model.dto.FeedbackSearchDto;
import com.heyya.model.dto.FeedbackUpdateDto;
import com.heyya.model.resp.Response;
import com.heyya.model.vo.FeedbackVo;
import com.heyya.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/feedback")
@Api(tags = "Feedback API")
public class FeedbackController extends BaseController {
    @Autowired
    private FeedbackService feedbackService;

    @ApiOperation(value = "反馈列表 API")
    @GetMapping
    public Response<PageInfo<FeedbackVo>> spark(@Valid FeedbackSearchDto dto) {
        return Response.success(feedbackService.page(dto));
    }

    @ApiOperation(value = "修改 API")
    @PutMapping
    public Response update(@RequestBody @Valid List<FeedbackUpdateDto> dto) {
        feedbackService.update(dto);
        return Response.success();
    }
}
