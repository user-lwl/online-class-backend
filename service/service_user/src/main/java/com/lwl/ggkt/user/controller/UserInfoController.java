package com.lwl.ggkt.user.controller;

import com.lwl.ggkt.model.user.UserInfo;
import com.lwl.ggkt.user.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author user-lwl
 * @createDate 2022/12/4 13:35
 */
@RequestMapping("/admin/user/userInfo")
@RestController
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;

    /**
     * 获取用户信息
     * @param id 用户id
     * @return 用户信息
     */
    @ApiOperation(value = "获取用户信息")
    @GetMapping("inner/getById/{id}")
    public UserInfo getById(@PathVariable Long id) {
        return userInfoService.getById(id);
    }
}
