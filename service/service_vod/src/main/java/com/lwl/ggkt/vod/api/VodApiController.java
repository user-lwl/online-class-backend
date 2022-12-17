package com.lwl.ggkt.vod.api;

import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags = "腾讯视频点播")
@RestController
@RequestMapping("/api/vod")
public class VodApiController {

    @Resource
    private VodService vodService;

    /**
     * 腾讯视频点播
     * @param courseId 课程id
     * @param videoId 视频id
     * @return 视频播放凭证
     */
    @GetMapping("getPlayAuth/{courseId}/{videoId}")
    public Result<Map<String, Object>> getPlayAuth(
            @ApiParam(value = "课程id", required = true)
            @PathVariable Long courseId,
            @ApiParam(value = "视频id", required = true)
            @PathVariable Long videoId) {
        Map<String, Object> result = vodService.getPlayAuth(courseId, videoId);
        return Result.ok(result);
    }
}