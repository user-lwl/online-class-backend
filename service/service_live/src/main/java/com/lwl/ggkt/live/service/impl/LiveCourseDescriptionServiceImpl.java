package com.lwl.ggkt.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.live.mapper.LiveCourseDescriptionMapper;
import com.lwl.ggkt.live.service.LiveCourseDescriptionService;
import com.lwl.ggkt.model.live.LiveCourseDescription;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author user-lwl
* @description 针对表【live_course_description(课程简介)】的数据库操作Service实现
* @createDate 2022-12-17 10:31:22
*/
@Service
public class LiveCourseDescriptionServiceImpl extends ServiceImpl<LiveCourseDescriptionMapper, LiveCourseDescription>
    implements LiveCourseDescriptionService {
    @Resource
    private LiveCourseDescriptionMapper liveCourseDescriptionMapper;

    /**
     * 获取直播课程描述信息
     * @param id id
     * @return 直播课程描述信息
     */
    @Override
    public LiveCourseDescription getLiveCourseById(Long id) {
        LambdaQueryWrapper<LiveCourseDescription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LiveCourseDescription::getLiveCourseId, id);
        return liveCourseDescriptionMapper.selectOne(queryWrapper);
    }
}




