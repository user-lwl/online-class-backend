package com.lwl.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.model.vod.Course;
import com.lwl.ggkt.model.vod.CourseDescription;
import com.lwl.ggkt.model.vod.Subject;
import com.lwl.ggkt.model.vod.Teacher;
import com.lwl.ggkt.vo.vod.*;
import com.lwl.ggkt.vod.mapper.CourseMapper;
import com.lwl.ggkt.vod.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author user-lwl
* @description 针对表【course(课程)】的数据库操作Service实现
* @createDate 2022-12-02 13:37:29
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService {
    @Resource
    private CourseMapper courseMapper;

    @Resource
    private SubjectService subjectService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private CourseDescriptionService courseDescriptionService;

    @Resource
    private VideoService videoService;

    @Resource
    private ChapterService chapterService;

    /**
     * 点播课程列表
     * @param pageParam 分页信息
     * @param courseQueryVo courseQueryVo
     * @return 课程列表分页信息
     */
    @Override
    public Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        // 获取条件值
        String title = courseQueryVo.getTitle();
        Long subjectId = courseQueryVo.getSubjectId(); // 二层分类
        Long subjectParentId = courseQueryVo.getSubjectParentId(); //一层分类
        Long teacherId = courseQueryVo.getTeacherId();
        // 判空，封装
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)) {
            queryWrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("teacher_id",teacherId);
        }
        // 分页查询
        Page<Course> pages = courseMapper.selectPage(pageParam, queryWrapper);
        List<Course> list = pages.getRecords();
        long totalCount = pages.getTotal();
        long totalPage = pages.getPages();
        //查询数据id  读取id名称封装  id -> 名称
        list.forEach(this::getNameById);
        // 封装
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("records",list);
        // 返回
        return map;
    }

    /**
     * 添加课程基本信息
     * @param courseFormVo 课程信息
     * @return 课程id
     */
    @Override
    public Long saveCourseInfo(CourseFormVo courseFormVo) {
        // 添加课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        courseMapper.insert(course);
        // 添加课程描述信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionService.save(courseDescription);
        return course.getId();
    }

    /**
     * 查询课程信息
     * @param id 课程id
     * @return 课程信息
     */
    @Override
    public CourseFormVo getCourseInfoById(Long id) {
        // 课程基本信息
        Course course = courseMapper.selectById(id);
        if (course == null) {
            return null;
        }
        // 课程描述信息
        CourseDescription courseDescription = courseDescriptionService.getById(id);
        // 封装
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course, courseFormVo);
        if (courseDescription != null) {
            courseFormVo.setDescription(courseDescription.getDescription());
        }
        return courseFormVo;
    }

    /**
     * 修改课程信息
     * @param courseFormVo 课程信息
     */
    @Override
    public void updateCourseId(CourseFormVo courseFormVo) {
        //修改课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        courseMapper.updateById(course);
        //修改课程描述信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionService.updateById(courseDescription);
    }

    /**
     * 根据id查询发布课程的信息
     * @param id 课程id
     * @return 课程的信息
     */
    @Override
    public CoursePublishVo getCoursePublishVo(Long id) {
        return courseMapper.selectCoursePublishVoById(id);
    }

    /**
     * 课程最终发布
     * @param id 课程id
     */
    @Override
    public void publishCourse(Long id) {
        Course course = courseMapper.selectById(id);
        course.setStatus(1);
        course.setPublishTime(new Date());
        courseMapper.updateById(course);
    }

    /**
     * 根据课程id删除课程
     * @param id 课程id
     */
    @Override
    public void removeCourseId(Long id) {
        // 删除小节
        videoService.removeVideoByCourseId(id);
        // 删除章节
        chapterService.removeChapterByCourseId(id);
        // 删除描述
        courseDescriptionService.removeById(id);
        // 删除课程
        courseMapper.deleteById(id);
    }

    /**
     * 根据课程分类查询课程列表（分页）
     * @param pageParam pageParam
     * @param courseQueryVo courseQueryVo
     * @return 课程列表
     */
    @Override
    public Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        //获取条件值
        Long subjectId = courseQueryVo.getSubjectId();//二级分类
        Long subjectParentId = courseQueryVo.getSubjectParentId();//一级分类
        Long teacherId = courseQueryVo.getTeacherId();//讲师
        String title = courseQueryVo.getTitle();//名称
        //封装条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(title)) {
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id",teacherId);
        }
        //调用方法查询
        Page<Course> pages = courseMapper.selectPage(pageParam, wrapper);

        long totalCount = pages.getTotal();//总记录数
        long totalPage = pages.getPages();//总页数
        long currentPage = pages.getCurrent();//当前页
        long size = pages.getSize();//每页记录数
        //每页数据集合
        List<Course> records = pages.getRecords();
        records.forEach(this::getTeacherAndSubjectName);

        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("currentPage", currentPage);
        map.put("records",records);
        map.put("size", size);
        return map;
    }

    /**
     * 根据ID查询课程
     * @param courseId 课程id
     * @return 课程
     */
    @Override
    public Map<String, Object> getInfoById(Long courseId) {
        //更新流量量
        Course course = courseMapper.selectById(courseId);
        course.setViewCount(course.getViewCount() + 1);
        courseMapper.updateById(course);

        Map<String, Object> map = new HashMap<>();
        CourseVo courseVo = courseMapper.selectCourseVoById(courseId);
        List<ChapterVo> chapterVoList = chapterService.getTreeList(courseId);
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        Teacher teacher = teacherService.getById(course.getTeacherId());

        //TODO后续完善
        Boolean isBuy = false;

        map.put("courseVo", courseVo);
        map.put("chapterVoList", chapterVoList);
        map.put("description", null != courseDescription ?
                courseDescription.getDescription() : "");
        map.put("teacher", teacher);
        map.put("isBuy", isBuy);//是否购买
        return map;
    }

    /**
     * 查询所有课程
     * @return 课程列表
     */
    @Override
    public List<Course> findList() {
        List<Course> list = courseMapper.selectList(null);
        list.forEach(this::getTeacherAndSubjectName);
        return list;
    }

    /**
     * 获取讲师和分类名称
     * @param course 课程
     */
    private void getTeacherAndSubjectName(Course course) {
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if(teacher != null) {
            course.getParam().put("teacherName",teacher.getName());
        }
        Subject subjectTwo = subjectService.getById(course.getSubjectId());
        if(subjectTwo != null) {
            course.getParam().put("subjectTitle",subjectTwo.getTitle());
        }
        Subject subjectOne = subjectService.getById(course.getSubjectParentId());
        if(subjectOne != null) {
            course.getParam().put("subjectParentTitle",subjectOne.getTitle());
        }
    }

    /**
     * 根据id对应名称进行封装
     * @param course Course
     */
    private void getNameById(Course course) {
        // 根据讲师id 获取讲师分类名称
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if (teacher != null) {
            String name = teacher.getName();
            course.getParam().put("teacherName", name);
        }
        // 根据课程分类id 获取课程分类名称
        Subject subOne = subjectService.getById(course.getSubjectParentId());
        if (subOne != null) {
            course.getParam().put("subjectParentTitle", subOne.getTitle());
        }
        Subject subTwo = subjectService.getById(course.getSubjectId());
        if (subTwo != null) {
            course.getParam().put("subjectTitle", subTwo.getTitle());
        }
    }
}




