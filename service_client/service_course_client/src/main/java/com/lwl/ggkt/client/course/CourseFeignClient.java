package com.lwl.ggkt.client.course;

import com.lwl.ggkt.model.vod.Course;
import com.lwl.ggkt.model.vod.Teacher;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "service-vod")
public interface CourseFeignClient {

    /**
     * 根据关键字查询课程
     * @param keyword 关键字
     * @return 课程列表
     */
    @ApiOperation("根据关键字查询课程")
    @GetMapping("/api/vod/course/inner/findByKeyword/{keyword}")
    List<Course> findByKeyword(@PathVariable String keyword);

    /**
     * 根据ID查询课程
     * @param courseId 课程id
     * @return 课程信息
     */
    @ApiOperation("根据ID查询课程")
    @GetMapping("/api/vod/course/inner/getById/{courseId}")
    Course getById(@PathVariable Long courseId);

    /**
     * 根据id查询
     * @param id id
     * @return 讲师信息
     */
    @GetMapping("/admin/vod/teacher/inner/getTeacher/{id}")
    Teacher getTeacherLive(@PathVariable Long id);
}