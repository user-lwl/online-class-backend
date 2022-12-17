package com.lwl.ggkt.vod.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwl.ggkt.model.vod.Course;
import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.vo.vod.CourseQueryVo;
import com.lwl.ggkt.vod.service.CourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author user-lwl
 * @createDate 2022/12/10 10:13
 */
@RestController
@RequestMapping("/api/vod/course")
public class CourseApiController {
    @Resource
    private CourseService courseService;

    /**
     * 根据关键字查询课程
     * @param keyword 关键字
     * @return 课程列表
     */
    @ApiOperation("根据关键字查询课程")
    @GetMapping("inner/findByKeyword/{keyword}")
    public List<Course> findByKeyword(
            @ApiParam(value = "关键字", required = true)
            @PathVariable String keyword){
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", keyword);
        return courseService.list(queryWrapper);
    }

    /**
     * 根据课程分类查询课程列表（分页）
     * @param subjectParentId subjectParentId
     * @param page 当前页
     * @param limit 每页记录数
     * @return 课程列表
     */
    @ApiOperation("根据课程分类查询课程列表")
    @GetMapping("{subjectParentId}/{page}/{limit}")
    public Result<Map<String, Object>> findPageCourse(@ApiParam(value = "课程一级分类ID", required = true) @PathVariable Long subjectParentId,
                                 @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                                 @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit) {
        //封装条件
        CourseQueryVo courseQueryVo = new CourseQueryVo();
        courseQueryVo.setSubjectParentId(subjectParentId);
        //创建page对象
        Page<Course> pageParam = new Page<>(page,limit);
        Map<String, Object> map = courseService.findPage(pageParam,courseQueryVo);
        return Result.ok(map);
    }

    /**
     * 根据ID查询课程
     * @param courseId 课程id
     * @return 课程
     */
    @ApiOperation("根据ID查询课程")
    @GetMapping("getInfo/{courseId}")
    public Result<Map<String, Object>> getInfo(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable Long courseId){
        Map<String, Object> map = courseService.getInfoById(courseId);
        return Result.ok(map);
    }

    /**
     * 根据ID查询课程
     * @param courseId 课程id
     * @return 课程信息
     */
    @ApiOperation("根据ID查询课程")
    @GetMapping("inner/getById/{courseId}")
    public Course getById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable Long courseId){
        return courseService.getById(courseId);
    }
}
