package com.heyya.api;

import com.github.pagehelper.PageInfo;
import com.heyya.config.auth.UserAuthContext;
import com.heyya.model.dto.BlockPersistDto;
import com.heyya.model.dto.BlockSearchDto;
import com.heyya.model.resp.Response;
import com.heyya.model.vo.BlockVo;
import com.heyya.service.BlockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/v1/block")
@Api(tags = "拉黑 API")
public class BlockController extends BaseController {

    @Autowired
    private BlockService blockService;

    @ApiOperation("新增")
    @PostMapping
    public Response save(@Valid @RequestBody BlockPersistDto dto) {
        dto.setFromUserId(UserAuthContext.getId());
        blockService.save(dto);
        return Response.success();
    }

    @ApiOperation("取消拉黑")
    @DeleteMapping("/{to-user-id}")
    public Response<Object> delete(@PathVariable(name = "to-user-id") Long id) {
        blockService.cancelBlack(id);
        return Response.SUCCESS;
    }

    @ApiOperation("拉黑列表")
    @GetMapping
    public Response<PageInfo<BlockVo>> search(@Valid BlockSearchDto dto) {
        dto.setUserId(UserAuthContext.getId());
        return Response.success(blockService.search(dto));
    }

}
