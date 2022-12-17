package com.lwl.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.model.vod.VideoVisitor;
import com.lwl.ggkt.vo.vod.VideoVisitorCountVo;
import com.lwl.ggkt.vod.mapper.VideoVisitorMapper;
import com.lwl.ggkt.vod.service.VideoVisitorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author user-lwl
* @description 针对表【video_visitor(视频来访者记录表)】的数据库操作Service实现
* @createDate 2022-12-02 13:37:30
*/
@Service
public class VideoVisitorServiceImpl extends ServiceImpl<VideoVisitorMapper, VideoVisitor>
    implements VideoVisitorService {
    @Resource
    private VideoVisitorMapper videoVisitorMapper;

    /**
     * 课程统计接口
     * @param courseId 课程id
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 课程统计结果
     */
    @Override
    public Map<String, Object> findCount(Long courseId, String startDate, String endDate) {
        List<VideoVisitorCountVo> videoVisitorVoList = videoVisitorMapper.findCount(courseId, startDate, endDate);
        Map<String, Object> map = new HashMap<>();
        // 封装数据
        // 日期
        List<String> dateList = videoVisitorVoList.stream().map(VideoVisitorCountVo::getJoinTime).collect(Collectors.toList());
        // 对应数量
        List<Integer> countList = videoVisitorVoList.stream().map(VideoVisitorCountVo::getUserCount).collect(Collectors.toList());
        map.put("xData", dateList);
        map.put("yData", countList);
        return map;
    }
}




