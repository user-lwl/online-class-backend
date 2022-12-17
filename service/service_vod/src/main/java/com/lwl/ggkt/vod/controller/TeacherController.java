package com.lwl.ggkt.vod.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwl.ggkt.model.vod.Teacher;
import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.vo.vod.TeacherQueryVo;
import com.lwl.ggkt.vod.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author user-lwl
 * @createDate 2022/12/1 14:22
 */
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping("/admin/vod/teacher")
//@CrossOrigin
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    /**
     * 查询所有讲师
     * @return 讲师列表
     */
    @ApiOperation("查询所有讲师")
    @GetMapping("/findAll")
    public Result<List<Teacher>> findAllTeacher() {
        List<Teacher> list = teacherService.list();
        return Result.ok(list);
    }

    /**
     * 逻辑删除讲师
     * @param id 讲师id
     * @return 是否成功
     */
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/remove/{id}")
    public Result<Boolean> removeTeacher(@PathVariable Long id) {
        boolean isSuccess = teacherService.removeById(id);
        if (isSuccess) {
            return Result.ok(true);
        }
        return Result.fail(false);
    }

    /**
     * 条件查询分页
     * @param current 当前页
     * @param limit 每页记录数
     * @param teacherQueryVo teacherQueryVo
     * @return 分页查询结果
     */
    @ApiOperation("条件查询分页")
    @GetMapping("/findQueryPage/{current}/{limit}")
    public Result<IPage<Teacher>> findPage(@PathVariable long current,
                           @PathVariable long limit,
                           @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {
        Page<Teacher> pageParam = new Page<>(current, limit);
        if (teacherQueryVo == null) {
            Page<Teacher> page = teacherService.page(pageParam, null);
            return Result.ok(page);
        }
        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String joinDateBegin = teacherQueryVo.getJoinDateBegin();
        String joinDateEnd = teacherQueryVo.getJoinDateEnd();
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(joinDateBegin)) {
            queryWrapper.ge("join_date", joinDateBegin);
        }
        if (!StringUtils.isEmpty(joinDateEnd)) {
            queryWrapper.le("join_data", joinDateEnd);
        }
        //分页查询
        IPage<Teacher> page = teacherService.page(pageParam, queryWrapper);
        return Result.ok(page);
    }

    /**
     * 添加讲师
     * @param teacher 讲师信息
     * @return 是否成功
     */
    @ApiOperation("添加讲师")
    @PostMapping("/saveTeacher")
    public Result<Boolean> saveTeacher(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.save(teacher);
        if (isSuccess) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 根据id查询
     * @param id 讲师id
     * @return 讲师信息
     */
    @ApiOperation("根据id查询")
    @GetMapping("/getTeacher/{id}")
    public Result<Teacher> getTeacher(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return Result.ok(teacher);
    }

    /**
     * 修改讲师信息
     * @param teacher 讲师信息
     * @return 是否成功
     */
    @ApiOperation("修改")
    @PostMapping("/updateTeacher")
    public Result<Boolean> updateTeacher(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.updateById(teacher);
        if (isSuccess) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 批量删除讲师
     * @param idList id列表
     * @return 是否成功
     */
    @ApiOperation("批量删除讲师")
    @DeleteMapping("/removeBatch")
    public Result<Boolean> removeBatch(@RequestBody List<Long> idList) {
        boolean isSuccess = teacherService.removeByIds(idList);
        if (isSuccess) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 根据id查询
     * @param id id
     * @return 讲师信息
     */
    @ApiOperation("根据id查询")
    @GetMapping("inner/getTeacher/{id}")
    public Teacher getTeacherLive(@PathVariable Long id) {
        return teacherService.getById(id);
    }
}
