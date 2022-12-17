package com.lwl.ggkt.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.model.order.OrderDetail;
import com.lwl.ggkt.order.mapper.OrderDetailMapper;
import com.lwl.ggkt.order.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
* @author user-lwl
* @description 针对表【order_detail(订单明细 订单明细)】的数据库操作Service实现
* @createDate 2022-12-03 20:55:06
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService {

}




