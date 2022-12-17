package com.lwl.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.model.vod.Video;
import com.lwl.ggkt.vod.mapper.VideoMapper;
import com.lwl.ggkt.vod.service.VideoService;
import com.lwl.ggkt.vod.service.VodService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
* @author user-lwl
* @description 针对表【video(课程视频)】的数据库操作Service实现
* @createDate 2022-12-02 13:37:30
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
    implements VideoService {
    @Resource
    private VideoMapper videoMapper;

    @Resource
    private VodService vodService;

    /**
     * 根据课程id删除小节 删除小节中的所有视频
     * @param id 课程id
     */
    @Override
    public void removeVideoByCourseId(Long id) {
        // 查所有小节
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        List<Video> videoList = videoMapper.selectList(queryWrapper);
        // 取id
        for (Video video : videoList) {
            String videoSourceId = video.getVideoSourceId();
            // 判空 删除视频
            if (!StringUtils.isEmpty(videoSourceId)) {
                vodService.removeVideo(videoSourceId);
            }
        }
        // 删除小节
        videoMapper.delete(queryWrapper);
    }

    /**
     * 根据id删除小节时删除视频
     * @param id 视频id
     */
    @Override
    public void removeVideoById(Long id) {
        // 查询小节
        Video video = videoMapper.selectById(id);
        // 获取id
        String videoSourceId = video.getVideoSourceId();
        // 判空
        if (!StringUtils.isEmpty(videoSourceId)) {
            // 删除
            vodService.removeVideo(videoSourceId);
        }
        // 删小节
        videoMapper.deleteById(id);
    }
}




