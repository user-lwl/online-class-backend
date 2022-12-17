package com.lwl.ggkt.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lwl.ggkt.exception.GgktException;
import com.lwl.ggkt.model.wechat.Menu;
import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.vo.wechat.MenuVo;
import com.lwl.ggkt.wechat.service.MenuService;
import com.lwl.ggkt.wechat.utils.ConstantPropertiesUtil;
import com.lwl.ggkt.wechat.utils.HttpClientUtils;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author user-lwl
 * @createDate 2022/12/4 14:30
 */
@RestController
@RequestMapping("/admin/wechat/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    /**
     * 根据id查询
     * @param id id
     * @return menu信息
     */
    @ApiOperation(value = "获取")
    @GetMapping("/get/{id}")
    public Result<Menu> get(@PathVariable Long id) {
        Menu menu = menuService.getById(id);
        return Result.ok(menu);
    }

    /**
     * 新增menu信息
     * @param menu menu
     * @return 是否成功
     */
    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public Result<Object> save(@RequestBody Menu menu) {
        menuService.save(menu);
        return Result.ok();
    }

    /**
     * 修改menu信息
     * @param menu menu
     * @return 是否成功
     */
    @ApiOperation(value = "修改")
    @PutMapping("/update")
    public Result<Object> updateById(@RequestBody Menu menu) {
        menuService.updateById(menu);
        return Result.ok();
    }

    /**
     * 删除menu
     * @param id id
     * @return 是否成功·
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/remove/{id}")
    public Result<Object> remove(@PathVariable Long id) {
        menuService.removeById(id);
        return Result.ok();
    }

    /**
     * 根据id列表删除
     * @param idList id列表
     * @return 是否成功
     */
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result<Object> batchRemove(@RequestBody List<Long> idList) {
        menuService.removeByIds(idList);
        return Result.ok();
    }

    /**
     * 获取所有菜单，按照一级和二级菜单封装
     * @return 菜单列表
     */
    @GetMapping("/findMenuInfo")
    public Result<List<MenuVo>> findMenuInfo() {
        List<MenuVo> list = menuService.findMenuInfo();
        return Result.ok(list);
    }

    /**
     * 获取所有一级菜单
     * @return 一级菜单列表
     */
    @GetMapping("/findOneMenuInfo")
    public Result<List<Menu>> findOneMenuInfo() {
        List<Menu> list = menuService.findMenuOneInfo();
        return Result.ok(list);
    }

    /**
     * 获取access_token
     * @return access_token
     */
    @GetMapping("/getAccessToken")
    public Result<String> getAccessToken() {
        try {
            //拼接请求地址
            String buffer = "https://api.weixin.qq.com/cgi-bin/token" +
                    "?grant_type=client_credential" +
                    "&appid=%s" +
                    "&secret=%s";
            //请求地址设置参数
            String url = String.format(buffer,
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //发送http请求
            String tokenString = HttpClientUtils.get(url);
            //获取access_token
            JSONObject jsonObject = JSON.parseObject(tokenString);
            String accessToken = jsonObject.getString("access_token");
            //返回
            return Result.ok(accessToken);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GgktException(20001, "获取access_token失败");
        }
    }

    /**
     * 同步菜单
     * @return 是否成功
     */
    @ApiOperation(value = "同步菜单")
    @GetMapping("/syncMenu")
    public Result<Object> createMenu() {
        menuService.syncMenu();
        return Result.ok();
    }

    /**
     * 公众号菜单删除
     * @return 是否成功
     */
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/removeMenu")
    public Result<Object> removeMenu() {
        menuService.removeMenu();
        return Result.ok();
    }
}
