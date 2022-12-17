package com.lwl.ggkt.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.live.mapper.LiveCourseConfigMapper;
import com.lwl.ggkt.live.service.LiveCourseConfigService;
import com.lwl.ggkt.model.live.LiveCourseConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author user-lwl
* @description 针对表【live_course_config(直播课程配置表)】的数据库操作Service实现
* @createDate 2022-12-17 10:31:22
*/
@Service
public class LiveCourseConfigServiceImpl extends ServiceImpl<LiveCourseConfigMapper, LiveCourseConfig>
    implements LiveCourseConfigService {
    @Resource
    private LiveCourseConfigMapper liveCourseConfigMapper;

    /**
     * 根据课程id查询配置信息
     * @param id 课程id
     * @return 配置信息
     */
    @Override
    public LiveCourseConfig getCourseConfigCourseId(Long id) {
        LambdaQueryWrapper<LiveCourseConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LiveCourseConfig::getLiveCourseId, id);
        return liveCourseConfigMapper.selectOne(queryWrapper);
    }
}




