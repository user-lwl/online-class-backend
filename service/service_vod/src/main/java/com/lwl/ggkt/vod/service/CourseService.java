package com.lwl.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lwl.ggkt.model.vod.Course;
import com.lwl.ggkt.vo.vod.CourseFormVo;
import com.lwl.ggkt.vo.vod.CoursePublishVo;
import com.lwl.ggkt.vo.vod.CourseQueryVo;

import java.util.List;
import java.util.Map;

/**
* @author user-lwl
* @description 针对表【course(课程)】的数据库操作Service
* @createDate 2022-12-02 13:37:29
*/
public interface CourseService extends IService<Course> {

    /**
     * 点播课程列表
     * @param pageParam 分页信息
     * @param courseQueryVo courseQueryVo
     * @return 课程列表分页信息
     */
    Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    /**
     * 添加课程基本信息
     * @param courseFormVo 课程信息
     * @return 课程id
     */
    Long saveCourseInfo(CourseFormVo courseFormVo);

    /**
     * 获取课程信息
     * @param id 课程id
     * @return 课程信息
     */
    CourseFormVo getCourseInfoById(Long id);

    /**
     * 修改课程信息
     * @param courseFormVo 课程信息
     */
    void updateCourseId(CourseFormVo courseFormVo);

    /**
     * 根据id查询发布课程的信息
     * @param id 课程id
     * @return 课程的信息
     */
    CoursePublishVo getCoursePublishVo(Long id);

    /**
     * 课程最终发布
     * @param id 课程id
     */
    void publishCourse(Long id);

    /**
     * 根据课程id删除课程
     * @param id 课程id
     */
    void removeCourseId(Long id);

    /**
     * 根据课程分类查询课程列表（分页）
     * @param pageParam pageParam
     * @param courseQueryVo courseQueryVo
     * @return 课程列表
     */
    Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    /**
     * 根据ID查询课程
     * @param courseId 课程id
     * @return 课程
     */
    Map<String, Object> getInfoById(Long courseId);

    /**
     * 查询所有课程
     * @return 课程列表
     */
    List<Course> findList();
}
