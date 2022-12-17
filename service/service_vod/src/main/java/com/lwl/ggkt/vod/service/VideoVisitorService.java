package com.lwl.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lwl.ggkt.model.vod.VideoVisitor;

import java.util.Map;

/**
* @author user-lwl
* @description 针对表【video_visitor(视频来访者记录表)】的数据库操作Service
* @createDate 2022-12-02 13:37:30
*/
public interface VideoVisitorService extends IService<VideoVisitor> {

    /**
     * 课程统计接口
     * @param courseId 课程id
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 课程统计结果
     */
    Map<String, Object> findCount(Long courseId, String startDate, String endDate);
}
