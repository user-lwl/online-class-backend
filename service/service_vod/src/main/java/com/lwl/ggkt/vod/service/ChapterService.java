package com.lwl.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lwl.ggkt.model.vod.Chapter;
import com.lwl.ggkt.vo.vod.ChapterVo;

import java.util.List;

/**
* @author user-lwl
* @description 针对表【chapter(课程)】的数据库操作Service
* @createDate 2022-12-02 13:37:29
*/
public interface ChapterService extends IService<Chapter> {

    /**
     * 大纲列表
     * @param courseId 课程id
     * @return 大纲列表
     */
    List<ChapterVo> getTreeList(Long courseId);

    /**
     * 根据课程id删除章节
     * @param id 课程id
     */
    void removeChapterByCourseId(Long id);
}
