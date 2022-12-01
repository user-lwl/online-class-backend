package com.lwl.ggkt.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lwl.ggkt.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@ApiModel(description = "OrderDetail")
@TableName("order_detail")
public class OrderDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程id")
	@TableField("course_id")
	private Long courseId;

	@ApiModelProperty(value = "课程名称")
	@TableField("course_name")
	private String courseName;

	@ApiModelProperty(value = "课程封面图片路径")
	@TableField("cover")
	private String cover;

	@ApiModelProperty(value = "订单编号")
	@TableField("order_id")
	private Long orderId;

	@ApiModelProperty(value = "用户id")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "原始金额")
	@TableField("origin_amount")
	private BigDecimal originAmount;

	@ApiModelProperty(value = "优惠劵减免金额")
	@TableField("coupon_reduce")
	private BigDecimal couponReduce;

	@ApiModelProperty(value = "最终金额")
	@TableField("final_amount")
	private BigDecimal finalAmount;

	@ApiModelProperty(value = "会话id 当前会话id 继承购物车中会话id")
	@TableField("session_id")
	private String sessionId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		OrderDetail that = (OrderDetail) o;
		return Objects.equals(getCourseId(), that.getCourseId()) && Objects.equals(getCourseName(), that.getCourseName()) && Objects.equals(getCover(), that.getCover()) && Objects.equals(getOrderId(), that.getOrderId()) && Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getOriginAmount(), that.getOriginAmount()) && Objects.equals(getCouponReduce(), that.getCouponReduce()) && Objects.equals(getFinalAmount(), that.getFinalAmount()) && Objects.equals(getSessionId(), that.getSessionId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getCourseId(), getCourseName(), getCover(), getOrderId(), getUserId(), getOriginAmount(), getCouponReduce(), getFinalAmount(), getSessionId());
	}
}