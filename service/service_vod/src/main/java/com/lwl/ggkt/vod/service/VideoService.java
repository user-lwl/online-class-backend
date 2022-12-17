package com.lwl.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lwl.ggkt.model.vod.Video;

/**
* @author user-lwl
* @description 针对表【video(课程视频)】的数据库操作Service
* @createDate 2022-12-02 13:37:30
*/
public interface VideoService extends IService<Video> {

    /**
     * 根据课程id删除小节
     * @param id 课程id
     */
    void removeVideoByCourseId(Long id);

    /**
     * 根据id删除小节时删除视频
     * @param id 视频id
     */
    void removeVideoById(Long id);
}
