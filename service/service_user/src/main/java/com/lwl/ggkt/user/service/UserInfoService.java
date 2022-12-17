package com.lwl.ggkt.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lwl.ggkt.model.user.UserInfo;

/**
* @author user-lwl
* @description 针对表【user_info(用户表)】的数据库操作Service
* @createDate 2022-12-04 10:57:04
*/
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 获取用户信息ByOpenId
     * @param openId openId
     * @return 用户信息
     */
    UserInfo getUserInfoOpenid(String openId);
}
