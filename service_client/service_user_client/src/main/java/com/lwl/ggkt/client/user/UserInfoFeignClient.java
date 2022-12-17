package com.lwl.ggkt.client.user;

import com.lwl.ggkt.model.user.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author user-lwl
 * @createDate 2022/12/4 13:48
 */
@FeignClient(value = "service-user")
public interface UserInfoFeignClient {

    /**
     * 获取用户信息
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("/admin/user/userInfo/inner/getById/{id}")
    UserInfo getById(@PathVariable Long id);

}
