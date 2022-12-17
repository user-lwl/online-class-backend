package com.lwl.ggkt.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.model.user.UserInfo;
import com.lwl.ggkt.user.mapper.UserInfoMapper;
import com.lwl.ggkt.user.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author user-lwl
* @description 针对表【user_info(用户表)】的数据库操作Service实现
* @createDate 2022-12-04 10:57:04
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 根据openid获取用户信息
     * @param openId openId
     * @return 用户信息
     */
    @Override
    public UserInfo getUserInfoOpenid(String openId) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id",openId);
        return userInfoMapper.selectOne(wrapper);
    }
}




