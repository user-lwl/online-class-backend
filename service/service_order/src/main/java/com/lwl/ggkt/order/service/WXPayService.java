package com.lwl.ggkt.order.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface WXPayService {
	/**
	 * 下单 小程序支付
	 * @param orderNo orderNo
	 * @return 参数
	 */
	Map<String, String> createJsapi(String orderNo);

	/**
	 * 根据订单号查询支付状态
	 * @param orderNo orderNo
	 * @return 支付状态
	 */
    Map<String, String> queryPayStatus(String orderNo);
}