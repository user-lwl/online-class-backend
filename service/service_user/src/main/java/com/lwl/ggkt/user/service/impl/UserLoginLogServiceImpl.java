package com.lwl.ggkt.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.model.user.UserLoginLog;
import com.lwl.ggkt.user.mapper.UserLoginLogMapper;
import com.lwl.ggkt.user.service.UserLoginLogService;
import org.springframework.stereotype.Service;

/**
* @author user-lwl
* @description 针对表【user_login_log(用户登陆记录表)】的数据库操作Service实现
* @createDate 2022-12-04 10:57:04
*/
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog>
    implements UserLoginLogService {

}




