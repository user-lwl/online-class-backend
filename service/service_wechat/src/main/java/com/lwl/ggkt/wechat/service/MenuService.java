package com.lwl.ggkt.wechat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lwl.ggkt.model.wechat.Menu;
import com.lwl.ggkt.vo.wechat.MenuVo;

import java.util.List;

/**
* @author user-lwl
* @description 针对表【menu(订单明细 订单明细)】的数据库操作Service
* @createDate 2022-12-04 14:28:49
*/
public interface MenuService extends IService<Menu> {

    /**
     * 获取所有菜单，按照一级和二级菜单封装
     * @return 菜单列表
     */
    List<MenuVo> findMenuInfo();

    /**
     * 获取所有一级菜单
     * @return 一级菜单列表
     */
    List<Menu> findMenuOneInfo();

    /**
     * 同步菜单
     */
    void syncMenu();

    /**
     * 删除公众号菜单
     */
    void removeMenu();
}
