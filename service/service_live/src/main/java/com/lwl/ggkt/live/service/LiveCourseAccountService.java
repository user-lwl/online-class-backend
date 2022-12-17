package com.lwl.ggkt.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lwl.ggkt.model.live.LiveCourseAccount;

/**
* @author user-lwl
* @description 针对表【live_course_account(直播课程账号表（受保护信息）)】的数据库操作Service
* @createDate 2022-12-17 10:31:22
*/
public interface LiveCourseAccountService extends IService<LiveCourseAccount> {

    /**
     * 查看账号信息
     * @param id id
     * @return 账号信息
     */
    LiveCourseAccount getByLiveCourseId(Long id);
}
