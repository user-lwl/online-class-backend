package com.lwl.ggkt.vod.controller;

import com.lwl.ggkt.model.vod.Video;
import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.vod.service.VideoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author user-lwl
 * @createDate 2022/12/2 13:55
 */
@RestController
@RequestMapping("/admin/vod/video")
//@CrossOrigin
public class VideoController {
    @Resource
    private VideoService videoService;

    /**
     * 根据id查询
     * @param id id
     * @return Video
     */
    @ApiOperation(value = "获取")
    @GetMapping("/get/{id}")
    public Result<Video> get(@PathVariable Long id) {
        Video video = videoService.getById(id);
        return Result.ok(video);
    }

    /**
     * 添加
     * @param video video
     * @return 是否成功
     */
    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public Result<Object> save(@RequestBody Video video) {
        videoService.save(video);
        return Result.ok();
    }

    /**
     * 修改
     * @param video video
     * @return 是否成功
     */
    @ApiOperation(value = "修改")
    @PutMapping("/update")
    public Result<Object> updateById(@RequestBody Video video) {
        videoService.updateById(video);
        return Result.ok();
    }

    /**
     * 根据id删除
     * @param id id
     * @return 是否成功
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/remove/{id}")
    public Result<Object> remove(@PathVariable Long id) {
        videoService.removeVideoById(id);
        return Result.ok();
    }
}
