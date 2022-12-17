package com.lwl.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.model.vod.Chapter;
import com.lwl.ggkt.model.vod.Video;
import com.lwl.ggkt.vo.vod.ChapterVo;
import com.lwl.ggkt.vo.vod.VideoVo;
import com.lwl.ggkt.vod.mapper.ChapterMapper;
import com.lwl.ggkt.vod.service.ChapterService;
import com.lwl.ggkt.vod.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author user-lwl
* @description 针对表【chapter(课程)】的数据库操作Service实现
* @createDate 2022-12-02 13:37:29
*/
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter>
    implements ChapterService {
    @Resource
    private ChapterMapper chapterMapper;

    @Resource
    private VideoService videoService;

    /**
     * 大纲列表
     * @param courseId 课程id
     * @return 大纲列表
     */
    @Override
    public List<ChapterVo> getTreeList(Long courseId) {
        List<ChapterVo> finalChapterList = new ArrayList<>();
        // 获取所有章节
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        List<Chapter> chapterList = chapterMapper.selectList(queryWrapper);
        // 获取所有小节
        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Video::getCourseId, courseId);
        List<Video> videoList = videoService.list(wrapper);
        // 封装章节
        for (Chapter chapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            finalChapterList.add(chapterVo);
            // 封装章节里的小节
            List<VideoVo> videoVoList = new ArrayList<>();
            for (Video video : videoList) {
                if (chapter.getId().equals(video.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            // 小节放入章节
            chapterVo.setChildren(videoVoList);
        }
        return finalChapterList;
    }

    /**
     * 根据课程id删除章节
     * @param id 课程id
     */
    @Override
    public void removeChapterByCourseId(Long id) {
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        chapterMapper.delete(queryWrapper);
    }
}




