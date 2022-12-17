package com.lwl.ggkt.vod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwl.ggkt.model.vod.Course;
import com.lwl.ggkt.vo.vod.CoursePublishVo;
import com.lwl.ggkt.vo.vod.CourseVo;

/**
* @author user-lwl
* @description 针对表【course(课程)】的数据库操作Mapper
* @createDate 2022-12-02 13:37:29
*/
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 根据id查询发布课程的信息
     * @param id 课程id
     * @return 课程的信息
     */
    CoursePublishVo selectCoursePublishVoById(Long id);

    /**
     * 课程详情数据
     * @param courseId 课程id
     * @return 课程详情数据
     */
    CourseVo selectCourseVoById(Long courseId);
}




