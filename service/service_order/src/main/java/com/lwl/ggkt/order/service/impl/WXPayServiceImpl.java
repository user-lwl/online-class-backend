package com.lwl.ggkt.order.service.impl;

import com.github.wxpay.sdk.WXPayUtil;
import com.lwl.ggkt.exception.GgktException;
import com.lwl.ggkt.order.service.WXPayService;
import com.lwl.ggkt.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WXPayServiceImpl implements WXPayService {

	public static final String NONCE_STR = "nonceStr";

	public static final String TIME_STAMP = "timeStamp";

	public static final String NONCE_STR_2 = "nonce_str";

	@Override
	public Map<String, String> createJsapi(String orderNo) {
		try {

			Map<String, String> paramMap = new HashMap<>();
			//1、设置参数
			paramMap.put("appid", "xxx");
			paramMap.put("mch_id", "1481962542");
			paramMap.put(NONCE_STR_2, WXPayUtil.generateNonceStr());
			paramMap.put("body", "test");
			paramMap.put("out_trade_no", orderNo);
			paramMap.put("total_fee", "1");
			paramMap.put("spbill_create_ip", "127.0.0.1");
			paramMap.put("notify_url", "http://glkt.atguigu.cn/api/order/wxPay/notify");
			paramMap.put("trade_type", "JSAPI");
//			paramMap.put("openid", "o1R-t5trto9c5sdYt6l1ncGmY5Y");
			//UserInfo userInfo = userInfoFeignClient.getById(paymentInfo.getUserId());
//			paramMap.put("openid", "oepf36SawvvS8Rdqva-Cy4flFFg");
			paramMap.put("openid", "oQTXC56lAy3xMOCkKCImHtHoLL");

			//2、HTTPClient来根据URL访问第三方接口并且传递参数
			HttpClientUtils client = new HttpClientUtils("https://api.mch.weixin.qq.com/pay/unifiedorder");

			//client设置参数
			client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, "MXb72b9RfshXZD4FRGV5KLqmv5bx9LT9"));
			client.setHttps(true);
			client.post();
			//3、返回第三方的数据
			String xml = client.getContent();
			Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
			if(null != resultMap.get("result_code")  && !"SUCCESS".equals(resultMap.get("result_code"))) {
				throw new GgktException(20001, "支付失败");
			}

			//4、再次封装参数
			Map<String, String> parameterMap = new HashMap<>();
			String prepayId = String.valueOf(resultMap.get("prepay_id"));
			String packages = "prepay_id=" + prepayId;
			parameterMap.put("appId", "xxx");
			parameterMap.put(NONCE_STR, resultMap.get(NONCE_STR_2));
			parameterMap.put("package", packages);
			parameterMap.put("signType", "MD5");
			parameterMap.put(TIME_STAMP, String.valueOf(new Date().getTime()));
			String sign = WXPayUtil.generateSignature(parameterMap, "MXb72b9RfshXZD4FRGV5KLqmv5bx9LT9");

			//返回结果
			Map<String, String> result = new HashMap<>();
			result.put("appId", "xxx");
			result.put(TIME_STAMP, parameterMap.get(TIME_STAMP));
			result.put(NONCE_STR, parameterMap.get(NONCE_STR));
			result.put("signType", "MD5");
			result.put("paySign", sign);
			result.put("package", packages);
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<>();
		}
	}

	/**
	 * 根据订单号查询支付状态
	 * @param orderNo orderNo
	 * @return 支付状态
	 */
	@Override
	public Map<String, String> queryPayStatus(String orderNo) {
		try {
			//1、封装参数
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("appid", "xxx");
			paramMap.put("mch_id", "xxx");
			paramMap.put("out_trade_no", orderNo);
			paramMap.put(NONCE_STR_2, WXPayUtil.generateNonceStr());

			//2、设置请求
			HttpClientUtils client = new HttpClientUtils("https://api.mch.weixin.qq.com/pay/orderquery");
			client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, "xxx"));
			client.setHttps(true);
			client.post();
			//3、返回第三方的数据
			String xml = client.getContent();
			//6、转成Map
			//7、返回
			return WXPayUtil.xmlToMap(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}