package com.lwl.ggkt.vod.controller;

import com.lwl.ggkt.model.vod.Chapter;
import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.vo.vod.ChapterVo;
import com.lwl.ggkt.vod.service.ChapterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author user-lwl
 * @createDate 2022/12/2 13:53
 */
@RestController
@RequestMapping("/admin/vod/chapter")
//@CrossOrigin
public class ChapterController {
    @Resource
    private ChapterService chapterService;

    /**
     * 大纲列表
     * @param courseId 课程id
     * @return 大纲列表
     */
    @ApiOperation("大纲列表")
    @GetMapping("/getNestedTreeList/{courseId}")
    public Result<List<ChapterVo>> getTreeList(@PathVariable Long courseId) {
        List<ChapterVo> list = chapterService.getTreeList(courseId);
        return Result.ok(list);
    }

    /**
     * 添加章节
     * @param chapter 章节
     * @return 是否成功
     */
    @PostMapping("/save")
    public Result<Object> save(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return Result.ok();
    }

    /**
     * 根据id查询
     * @param id id
     * @return 章节查询结果
     */
    @GetMapping("/get/{id}")
    public Result<Chapter> get(@PathVariable Long id) {
        Chapter chapter = chapterService.getById(id);
        return Result.ok(chapter);
    }

    /**
     * 修改章节
     * @param chapter 章节信息
     * @return 是否成功
     */
    @PostMapping("/update")
    public Result<Object> update(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return Result.ok();
    }

    /**
     * 根据id删除章节
     * @param id 章节id
     * @return 是否成功
     */
    @DeleteMapping("/remove/{id}")
    public Result<Object> remove(@PathVariable Long id) {
        chapterService.removeById(id);
        return Result.ok();
    }
}
