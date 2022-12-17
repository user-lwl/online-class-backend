package com.lwl.ggkt.order.api;

import com.lwl.ggkt.order.service.OrderInfoService;
import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.vo.order.OrderFormVo;
import com.lwl.ggkt.vo.order.OrderInfoVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/order/orderInfo")
public class OrderInfoApiController {

    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 新增点播课程订单
     * @param orderFormVo orderFormVo
     * @return 订单id
     */
    @ApiOperation("新增点播课程订单")
    @PostMapping("submitOrder")
    public Result<Long> submitOrder(@RequestBody OrderFormVo orderFormVo) {
        //返回订单id
        Long orderId = orderInfoService.submitOrder(orderFormVo);
        return Result.ok(orderId);
    }

    /**
     * 根据订单号查询订单信息
     * @param id 订单id
     * @return 订单信息
     */
    @ApiOperation(value = "获取")
    @GetMapping("getInfo/{id}")
    public Result<OrderInfoVo> getInfo(@PathVariable Long id) {
        OrderInfoVo orderInfoVo = orderInfoService.getOrderInfoVoById(id);
        return Result.ok(orderInfoVo);
    }
}