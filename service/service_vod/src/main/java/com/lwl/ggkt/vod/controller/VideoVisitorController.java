package com.lwl.ggkt.vod.controller;

import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.vod.service.VideoVisitorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author user-lwl
 * @createDate 2022/12/3 15:25
 */
@RestController
@RequestMapping("/admin/vod/videoVisitor")
//@CrossOrigin
public class VideoVisitorController {
    @Resource
    private VideoVisitorService videoVisitorService;

    /**
     * 课程统计接口
     * @param courseId 课程id
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 统计数据
     */
    @GetMapping("/findCount/{courseId}/{startDate}/{endDate}")
    public Result<Map<String, Object>> findCount(@PathVariable Long courseId,
                                 @PathVariable String startDate,
                                 @PathVariable String endDate) {
        Map<String, Object> map = videoVisitorService.findCount(courseId, startDate, endDate);
        return Result.ok(map);
    }
}
