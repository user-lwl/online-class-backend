package com.lwl.ggkt.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lwl.ggkt.enums.PaymentStatus;
import com.lwl.ggkt.enums.PaymentType;
import com.lwl.ggkt.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Data
@ApiModel(description = "PaymentInfo")
@TableName("payment_info")
public class PaymentInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "对外业务编号")
	@TableField("out_trade_no")
	private String outTradeNo;

	@ApiModelProperty(value = "订单编号")
	@TableField("order_id")
	private Long orderId;

	@ApiModelProperty(value = "用户id")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "支付宝交易编号")
	@TableField("alipay_trade_no")
	private String alipayTradeNo;

	@ApiModelProperty(value = "支付金额")
	@TableField("total_amount")
	private BigDecimal totalAmount;

	@ApiModelProperty(value = "交易内容")
	@TableField("trade_body")
	private String tradeBody;

	@ApiModelProperty(value = "paymentType")
	@TableField("payment_type")
	private PaymentType paymentType;

	@ApiModelProperty(value = "支付状态")
	@TableField("payment_status")
	private PaymentStatus paymentStatus;

	@ApiModelProperty(value = "回调信息")
	@TableField("callback_content")
	private String callbackContent;

	@ApiModelProperty(value = "回调时间")
	@TableField("callback_time")
	private Date callbackTime;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		PaymentInfo that = (PaymentInfo) o;
		return Objects.equals(getOutTradeNo(), that.getOutTradeNo()) && Objects.equals(getOrderId(), that.getOrderId()) && Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getAlipayTradeNo(), that.getAlipayTradeNo()) && Objects.equals(getTotalAmount(), that.getTotalAmount()) && Objects.equals(getTradeBody(), that.getTradeBody()) && getPaymentType() == that.getPaymentType() && getPaymentStatus() == that.getPaymentStatus() && Objects.equals(getCallbackContent(), that.getCallbackContent()) && Objects.equals(getCallbackTime(), that.getCallbackTime());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getOutTradeNo(), getOrderId(), getUserId(), getAlipayTradeNo(), getTotalAmount(), getTradeBody(), getPaymentType(), getPaymentStatus(), getCallbackContent(), getCallbackTime());
	}
}