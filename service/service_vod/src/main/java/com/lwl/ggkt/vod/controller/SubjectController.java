package com.lwl.ggkt.vod.controller;

import com.lwl.ggkt.model.vod.Subject;
import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.vod.service.SubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author user-lwl
 * @createDate 2022/12/2 12:48
 */
@RestController
@RequestMapping("/admin/vod/subject")
//@CrossOrigin
public class SubjectController {
    @Resource
    private SubjectService subjectService;

    /**
     * 课程分类列表，懒加载，每次查询一层数据
     * @param id 课程id
     * @return 分类列表
     */
    @ApiOperation("课程分类列表")
    @GetMapping("/getChildSubject/{id}")
    public Result<List<Subject>> getChildSubject(@PathVariable Long id) {
        List<Subject> list = subjectService.selectSubjectList(id);
        return Result.ok(list);
    }

    /**
     * 课程分类导出
     * @param response response
     */
    @ApiOperation("课程分类导出")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) {
        subjectService.exportData(response);
    }

    /**
     * 课程分类导入
     * @param file 文件
     * @return 是否成功
     */
    @ApiOperation("课程分类导入")
    @PostMapping("/importData")
    public Result<Object> importData(MultipartFile file) {
        subjectService.importData(file);
        return Result.ok();
    }
}
