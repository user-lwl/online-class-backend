package com.lwl.ggkt.order.api;

import com.lwl.ggkt.order.service.OrderInfoService;
import com.lwl.ggkt.order.service.WXPayService;
import com.lwl.ggkt.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags = "微信支付接口")
@RestController
@RequestMapping("/api/order/wxPay")
public class WXPayController {
    @Resource
    private WXPayService wxPayService;

    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 下单 小程序支付
     * @param orderNo orderNo
     * @return 参数
     */
    @ApiOperation(value = "下单 小程序支付")
    @GetMapping("/createJsapi/{orderNo}")
    public Result<Map<String, String>> createJsapi(
            @ApiParam(name = "orderNo", value = "订单No", required = true)
            @PathVariable("orderNo") String orderNo) {
        Map<String, String> result = wxPayService.createJsapi(orderNo);
        return Result.ok(result);
    }

    /**
     * 查询支付状态
     * @param orderNo orderNo
     * @return 支付状态
     */
    @ApiOperation(value = "查询支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public Result<Object> queryPayStatus(
            @ApiParam(name = "orderNo", value = "订单No", required = true)
            @PathVariable("orderNo") String orderNo) {

        System.out.println("orderNo:"+orderNo);
        //调用查询接口
        Map<String, String> resultMap = wxPayService.queryPayStatus(orderNo);
        if (resultMap == null) {//出错
            return Result.fail(null).message("支付出错");
        }
        if ("SUCCESS".equals(resultMap.get("trade_state"))) {//如果成功
            //更改订单状态，处理支付结果
            String outTradeNo = resultMap.get("out_trade_no");
            System.out.println("out_trade_no:"+outTradeNo);
            orderInfoService.updateOrderStatus(outTradeNo);
            return Result.ok(null).message("支付成功");
        }
        return Result.ok().message("支付中");
    }
}