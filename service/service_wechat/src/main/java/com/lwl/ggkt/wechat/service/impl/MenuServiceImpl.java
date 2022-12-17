package com.lwl.ggkt.wechat.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.exception.GgktException;
import com.lwl.ggkt.model.wechat.Menu;
import com.lwl.ggkt.vo.wechat.MenuVo;
import com.lwl.ggkt.wechat.mapper.MenuMapper;
import com.lwl.ggkt.wechat.service.MenuService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author user-lwl
* @description 针对表【menu(订单明细 订单明细)】的数据库操作Service实现
* @createDate 2022-12-04 14:28:49
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService {
    @Resource
    private MenuMapper menuMapper;

    @Resource
    private WxMpService wxMpService;

    /**
     * 获取所有菜单，按照一级和二级菜单封装
     * @return 菜单列表
     */
    @Override
    public List<MenuVo> findMenuInfo() {
        // list
        List<MenuVo> finalMenuList = new ArrayList<>();
        // 查所有数据
        List<Menu> menuList = menuMapper.selectList(null);
        // get一级菜单数据
        List<Menu> oneMenuList = menuList.stream()
                .filter(menu -> menu.getParentId() == 0)
                .collect(Collectors.toList());
        // 封装一级菜单数据->list
        for (Menu oneMenu : oneMenuList) {
            MenuVo oneMenuVo = new MenuVo();
            BeanUtils.copyProperties(oneMenu, oneMenuVo);
            // 封装二级菜单数据
            List<Menu> twoMenuList = menuList.stream()
                    .filter(menu -> menu.getParentId().equals(oneMenu.getId()))
                    .collect(Collectors.toList());
            List<MenuVo> children = new ArrayList<>();
            for (Menu twoMenu : twoMenuList) {
                MenuVo twoMenuVo = new MenuVo();
                BeanUtils.copyProperties(twoMenu, twoMenuVo);
                children.add(twoMenuVo);
            }
            oneMenuVo.setChildren(children);
            finalMenuList.add(oneMenuVo);
        }
        return finalMenuList;
    }

    /**
     * 获取所有一级菜单
     * @return 一级菜单列表
     */
    @Override
    public List<Menu> findMenuOneInfo() {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        return menuMapper.selectList(queryWrapper);
    }

    /**
     * 同步菜单
     */
    @Override
    public void syncMenu() {
        // 获取菜单数据
        List<MenuVo> menuVoList = this.findMenuInfo();
        JSONArray buttonList = new JSONArray();
        for (MenuVo oneMenuVo : menuVoList) {
            // 一级菜单
            JSONObject one = new JSONObject();
            one.put("name", oneMenuVo.getName());
            // 二级菜单
            JSONArray subButton = new JSONArray();
            for (MenuVo twoMenuVo : oneMenuVo.getChildren()) {
                JSONObject view = new JSONObject();
                view.put("type", twoMenuVo.getType());
                if(twoMenuVo.getType().equals("view")) {
                    view.put("name", twoMenuVo.getName());
                    view.put("url", "http://ggkt2.vipgz1.91tunnel.com/#"
                            +twoMenuVo.getUrl());
                } else {
                    view.put("name", twoMenuVo.getName());
                    view.put("key", twoMenuVo.getMeunKey());
                }
                subButton.add(view);
            }
            one.put("sub_button", subButton);
            buttonList.add(one);
        }
        JSONObject button = new JSONObject();
        button.put("button", buttonList);
        try {
            this.wxMpService.getMenuService().menuCreate(button.toJSONString());
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new GgktException(20001, "公众号菜单同步失败");
        }
    }

    /**
     * 删除公众号菜单
     */
    @Override
    public void removeMenu() {
        try {
            wxMpService.getMenuService().menuDelete();
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new GgktException(20001, "公众号菜单删除失败");
        }
    }
}




