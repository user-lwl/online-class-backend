package com.lwl.ggkt.live.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwl.ggkt.live.service.LiveCourseAccountService;
import com.lwl.ggkt.live.service.LiveCourseService;
import com.lwl.ggkt.model.live.LiveCourse;
import com.lwl.ggkt.model.live.LiveCourseAccount;
import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.vo.live.LiveCourseConfigVo;
import com.lwl.ggkt.vo.live.LiveCourseFormVo;
import com.lwl.ggkt.vo.live.LiveCourseVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value="/admin/live/liveCourse")
public class LiveCourseController {

    @Resource
    private LiveCourseService liveCourseService;

    @Resource
    private LiveCourseAccountService liveCourseAccountService;

    /**
     * 获取分页列表
     * @param page 当前页
     * @param limit 每页记录数
     * @return 分页列表
     */
    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result<IPage<LiveCourse>> index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<LiveCourse> pageParam = new Page<>(page, limit);
        IPage<LiveCourse> pageModel = liveCourseService.selectPage(pageParam);
        return Result.ok(pageModel);
    }

    /**
     * 新增直播课程
     * @param liveCourseFormVo liveCourseFormVo
     * @return 是否成功
     */
    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result<Object> save(@RequestBody LiveCourseFormVo liveCourseFormVo) {
        liveCourseService.saveLive(liveCourseFormVo);
        return Result.ok();
    }

    /**
     * 删除直播课程
     * @param id id
     * @return 是否成功
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result<Object> remove(@PathVariable Long id) {
        liveCourseService.removeLive(id);
        return Result.ok();
    }

    /**
     * 查询直播课程基本信息
     * @param id id
     * @return 直播课程信息
     */
    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result<LiveCourse> get(@PathVariable Long id) {
        LiveCourse liveCourse = liveCourseService.getById(id);
        return Result.ok(liveCourse);
    }

    /**
     * 查询直播课程详细信息(包括描述信息)
     * @param id id
     * @return 直播课程详细信息
     */
    @ApiOperation(value = "获取")
    @GetMapping("getInfo/{id}")
    public Result<LiveCourseFormVo> getInfo(@PathVariable Long id) {
        LiveCourseFormVo liveCourseFormVo = liveCourseService.getLiveCourseFormVo(id);
        return Result.ok(liveCourseFormVo);
    }

    /**
     * 修改直播课程信息
     * @param liveCourseFormVo liveCourseFormVo
     * @return 是否成功
     */
    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result<Object> updateById(@RequestBody LiveCourseFormVo liveCourseFormVo) {
        liveCourseService.updateLiveById(liveCourseFormVo);
        return Result.ok();
    }

    /**
     * 查看账号信息
     * @param id id
     * @return 账号信息
     */
    @ApiOperation(value = "查看账号信息")
    @GetMapping("getLiveCourseAccount/{id}")
    public Result<LiveCourseAccount> getLiveCourseAccount(@PathVariable Long id) {
        LiveCourseAccount liveCourseAccount = liveCourseAccountService.getByLiveCourseId(id);
        return Result.ok(liveCourseAccount);
    }

    /**
     * 查看配置信息
     * @param id id
     * @return 配置信息
     */
    @ApiOperation(value = "查看配置信息")
    @GetMapping("getCourseConfig/{id}")
    public Result<LiveCourseConfigVo> getCourseConfig(@PathVariable Long id) {
        LiveCourseConfigVo liveCourseConfigVo = liveCourseService.getCourseConfig(id);
        return Result.ok(liveCourseConfigVo);
    }

    /**
     * 修改直播配置
     * @param liveCourseConfigVo liveCourseConfigVo
     * @return 是否成功
     */
    @ApiOperation(value = "修改配置")
    @PutMapping("updateConfig")
    public Result<Object> updateConfig(@RequestBody LiveCourseConfigVo liveCourseConfigVo) {
        liveCourseService.updateConfig(liveCourseConfigVo);
        return Result.ok();
    }

    /**
     * 获取最近的直播
     * @return 最近的直播课程
     */
    @ApiOperation(value = "获取最近的直播")
    @GetMapping("findLatelyList")
    public Result<List<LiveCourseVo>> findLatelyList() {
        List<LiveCourseVo> liveCourseVoList = liveCourseService.findLatelyList();
        return Result.ok(liveCourseVoList);
    }
}