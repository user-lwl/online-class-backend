package com.lwl.ggkt.live.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwl.ggkt.model.live.LiveCourse;
import com.lwl.ggkt.vo.live.LiveCourseVo;

import java.util.List;

/**
* @author user-lwl
* @description 针对表【live_course(直播课程表)】的数据库操作Mapper
* @createDate 2022-12-17 10:31:22
*/
public interface LiveCourseMapper extends BaseMapper<LiveCourse> {

    /**
     * 获取最近的直播课程
     * @return 最近的直播课程
     */
    List<LiveCourseVo> findLatelyList();
}




