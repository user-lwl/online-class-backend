package com.lwl.ggkt.vod.service;

import java.util.Map;

/**
 * @author user-lwl
 * @createDate 2022/12/3 16:43
 */
public interface VodService {
    /**
     * 上传视频接口
     * @return 视频id
     */
    String uploadVideo();

    /**
     * 删除腾讯云视频
     * @param fileId 视频id
     */
    void removeVideo(String fileId);

    /**
     * 获取视频播放凭证
     * @param courseId 课程id
     * @param videoId 视频id
     * @return 视频播放凭证
     */
    Map<String, Object> getPlayAuth(Long courseId, Long videoId);
}
