package com.lwl.ggkt.vod.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwl.ggkt.model.vod.Course;
import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.vo.vod.CourseFormVo;
import com.lwl.ggkt.vo.vod.CoursePublishVo;
import com.lwl.ggkt.vo.vod.CourseQueryVo;
import com.lwl.ggkt.vod.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author user-lwl
 * @createDate 2022/12/2 13:54
 */
@RestController
@RequestMapping("/admin/vod/course")
//@CrossOrigin
public class CourseController {
    @Resource
    private CourseService courseService;

    /**
     * 点播课程列表
     * @param page 当前页
     * @param limit 每页记录数
     * @param courseQueryVo courseQueryVo
     * @return 点播课程列表
     */
    @ApiOperation("点播课程列表")
    @GetMapping("/{page}/{limit}")
    public Result<Map<String, Object>> courseList(@PathVariable Long page,
                             @PathVariable Long limit,
                             CourseQueryVo courseQueryVo) {
        Page<Course> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.findPageCourse(pageParam, courseQueryVo);
        return Result.ok(map);
    }

    /**
     * 添加课程的基本信息
     * @param courseFormVo 课程信息
     * @return 课程id
     */
    @ApiOperation("添加课程的基本信息")
    @PostMapping("/save")
    public Result<Long> save(@RequestBody CourseFormVo courseFormVo) {
        Long courseId = courseService.saveCourseInfo(courseFormVo);
        return Result.ok(courseId);
    }

    /**
     * 获取课程信息
     * @param id 课程id
     * @return 课程信息
     */
    @GetMapping("/get/{id}")
    public Result<CourseFormVo> get(@PathVariable Long id) {
        CourseFormVo courseFormVo = courseService.getCourseInfoById(id);
        return Result.ok(courseFormVo);
    }

    /**
     * 修改课程信息
     * @param courseFormVo 课程信息
     * @return 是否成功
     */
    @PostMapping("/update")
    public Result<Long> update(@RequestBody CourseFormVo courseFormVo) {
        courseService.updateCourseId(courseFormVo);
        return Result.ok(courseFormVo.getId());
    }

    /**
     * 根据id查询发布课程的信息
     * @param id 课程id
     * @return 课程信息
     */
    @ApiOperation("根据id查询发布课程的信息")
    @GetMapping("/getCoursePublishVo/{id}")
    public Result<CoursePublishVo> getCoursePublishVo(@PathVariable Long id) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(id);
        return Result.ok(coursePublishVo);
    }

    /**
     * 课程最终发布
     * @param id 课程id
     * @return 是否成功
     */
    @ApiOperation("课程的最终发布")
    @PutMapping("/publishCourse/{id}")
    public Result<Object> publishCourse(@PathVariable Long id) {
        courseService.publishCourse(id);
        return Result.ok();
    }

    /**
     * 根据id删除课程
     * @param id 课程id
     * @return 是否成功
     */
    @ApiOperation("删除课程")
    @DeleteMapping("/remove/{id}")
    public Result<Object> remove(@PathVariable Long id) {
        courseService.removeCourseId(id);
        return Result.ok();
    }

    /**
     * 查询所有课程
     * @return 课程列表
     */
    @GetMapping("findAll")
    public Result<List<Course>> findAll() {
        List<Course> list = courseService.findList();
        return Result.ok(list);
    }
}
