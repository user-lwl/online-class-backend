package com.lwl.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lwl.ggkt.model.vod.Subject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author user-lwl
* @description 针对表【subject(课程科目)】的数据库操作Service
* @createDate 2022-12-02 12:45:50
*/
public interface SubjectService extends IService<Subject> {

    /**
     * 课程分类列表
     * @param id 课程id
     * @return 分类列表
     */
    List<Subject> selectSubjectList(Long id);

    /**
     * 课程分类导出
     * @param response response
     */
    void exportData(HttpServletResponse response);

    /**
     * 课程分类导入
     * @param file 文件
     */
    void importData(MultipartFile file);
}
