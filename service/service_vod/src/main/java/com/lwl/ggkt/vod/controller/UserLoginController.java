package com.lwl.ggkt.vod.controller;

import com.lwl.ggkt.result.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author user-lwl
 * @createDate 2022/12/1 16:41
 */
@RestController
@RequestMapping("/admin/vod/user")
//@CrossOrigin
public class UserLoginController {

    /**
     * 登陆
     * @return result
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", "admin-token");
        return Result.ok(map);
    }

    /**
     * info
     * @return result
     */
    @GetMapping("/info")
    public Result<Map<String, Object>> info() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles", "admin");
        map.put("introduction", "I am a super administrator");
        map.put("avatar", ""); // 头像
        map.put("name", "Super Admin");
        return Result.ok(map);
    }

    /**
     * 退出
     * @return result
     */
    @PostMapping("logout")
    public Result<Object> logout(){
        return Result.ok();
    }
}
