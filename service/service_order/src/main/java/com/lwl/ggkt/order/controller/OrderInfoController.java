package com.lwl.ggkt.order.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwl.ggkt.model.order.OrderInfo;
import com.lwl.ggkt.order.service.OrderInfoService;
import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.vo.order.OrderInfoQueryVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author user-lwl
 * @createDate 2022/12/3 20:59
 */
@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderInfoController {
    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 查询订单列表
     * @param page 当前页
     * @param limit 每页记录数
     * @param orderInfoQueryVo orderInfoQueryVo
     * @return 订单列表
     */
    @GetMapping("/{page}/{limit}")
    public Result<Map<String, Object>> listOrder(@PathVariable Long page,
                            @PathVariable Long limit,
                            OrderInfoQueryVo orderInfoQueryVo) {
        Page<OrderInfo> pageParam = new Page<>(page, limit);
        Map<String, Object> map = orderInfoService.selectOrderInfoPage(pageParam, orderInfoQueryVo);
        return Result.ok(map);
    }
}
