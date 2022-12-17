package com.lwl.ggkt.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.live.mapper.LiveCourseAccountMapper;
import com.lwl.ggkt.live.service.LiveCourseAccountService;
import com.lwl.ggkt.model.live.LiveCourseAccount;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author user-lwl
* @description 针对表【live_course_account(直播课程账号表（受保护信息）)】的数据库操作Service实现
* @createDate 2022-12-17 10:31:22
*/
@Service
public class LiveCourseAccountServiceImpl extends ServiceImpl<LiveCourseAccountMapper, LiveCourseAccount>
    implements LiveCourseAccountService {
    @Resource
    private LiveCourseAccountMapper liveCourseAccountMapper;

    /**
     * 查看账号信息
     * @param id id
     * @return 账号信息
     */
    @Override
    public LiveCourseAccount getByLiveCourseId(Long id) {
        LambdaQueryWrapper<LiveCourseAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LiveCourseAccount::getLiveCourseId, id);
        return liveCourseAccountMapper.selectOne(queryWrapper);
    }
}




