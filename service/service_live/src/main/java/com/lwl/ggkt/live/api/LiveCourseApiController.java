package com.lwl.ggkt.live.api;

import com.alibaba.fastjson.JSONObject;
import com.lwl.ggkt.live.service.LiveCourseService;
import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.utils.AuthContextHolder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("api/live/liveCourse")
public class LiveCourseApiController {

	@Resource
	private LiveCourseService liveCourseService;

    /**
     * 获取用户access_token
     * @param id id
     * @return JSONObject
     */
    @ApiOperation(value = "获取用户access_token")
    @GetMapping("getPlayAuth/{id}")
    public Result<JSONObject> getPlayAuth(@PathVariable Long id) {
        JSONObject object = liveCourseService.getAccessToken(id, AuthContextHolder.getUserId());
        return Result.ok(object);
    }

    /**
     * 根据ID查询课程
     * @param courseId 课程id
     * @return 课程信息
     */
    @ApiOperation("根据ID查询课程")
    @GetMapping("getInfo/{courseId}")
    public Result<Map<String, Object>> getInfo(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable Long courseId){
        Map<String, Object> map = liveCourseService.getInfoById(courseId);
        return Result.ok(map);
    }
}