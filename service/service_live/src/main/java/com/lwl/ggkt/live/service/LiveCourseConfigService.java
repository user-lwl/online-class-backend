package com.lwl.ggkt.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lwl.ggkt.model.live.LiveCourseConfig;

/**
* @author user-lwl
* @description 针对表【live_course_config(直播课程配置表)】的数据库操作Service
* @createDate 2022-12-17 10:31:22
*/
public interface LiveCourseConfigService extends IService<LiveCourseConfig> {

    /**
     * 根据课程id查询配置信息
     * @param id 课程id
     * @return 配置信息
     */
    LiveCourseConfig getCourseConfigCourseId(Long id);
}
