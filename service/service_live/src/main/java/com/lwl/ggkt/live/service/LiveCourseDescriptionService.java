package com.lwl.ggkt.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lwl.ggkt.model.live.LiveCourseDescription;

/**
* @author user-lwl
* @description 针对表【live_course_description(课程简介)】的数据库操作Service
* @createDate 2022-12-17 10:31:22
*/
public interface LiveCourseDescriptionService extends IService<LiveCourseDescription> {

    /**
     * 获取直播课程描述信息
     * @param id id
     * @return 直播课程描述信息
     */
    LiveCourseDescription getLiveCourseById(Long id);
}
