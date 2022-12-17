package com.lwl.ggkt.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lwl.ggkt.model.order.OrderInfo;
import com.lwl.ggkt.vo.order.OrderFormVo;
import com.lwl.ggkt.vo.order.OrderInfoQueryVo;
import com.lwl.ggkt.vo.order.OrderInfoVo;

import java.util.Map;

/**
* @author user-lwl
* @description 针对表【order_info(订单表 订单表)】的数据库操作Service
* @createDate 2022-12-03 20:55:06
*/
public interface OrderInfoService extends IService<OrderInfo> {

    /**
     * 分页查询订单列表
     * @param pageParam pageParam
     * @param orderInfoQueryVo orderInfoQueryVo
     * @return 分页订单列表
     */
    Map<String, Object> selectOrderInfoPage(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo);

    /**
     * 生成点播课程订单
     * @param orderFormVo orderFormVo
     * @return 订单id
     */
    Long submitOrder(OrderFormVo orderFormVo);

    /**
     * 根据订单号查询订单信息
     * @param id 订单id
     * @return 订单信息
     */
    OrderInfoVo getOrderInfoVoById(Long id);

    /**
     * 更新订单状态
     * @param outTradeNo outTradeNo
     */
    void updateOrderStatus(String outTradeNo);
}
