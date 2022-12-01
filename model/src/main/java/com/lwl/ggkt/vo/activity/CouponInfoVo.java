package com.lwl.ggkt.vo.activity;

import com.lwl.ggkt.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

@Data
@ApiModel(description = "CouponInfo")
public class CouponInfoVo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long id;

	@ApiModelProperty(value = "优惠券领取表id")
	private Long couponUseId;

	@ApiModelProperty(value = "是否可用")
	private Integer available;

	@ApiModelProperty(value = "优惠卷名字")
	private String name;

	@ApiModelProperty(value = "卡有效开始时间 (时间戳, 单位毫秒)")
	private Long startAt;

	@ApiModelProperty(value = "卡有效结束时间 (时间戳, 单位毫秒)")
	private Long endAt;

	@ApiModelProperty(value = "满减条件")
	private String condition;

	@ApiModelProperty(value = "描述信息，优惠券可用时展示")
	private String description;

	@ApiModelProperty(value = "不可用原因，优惠券不可用时展示")
	private String reason;

	@ApiModelProperty(value = "折扣券优惠金额，单位分")
	private String value;

	@ApiModelProperty(value = "折扣券优惠金额文案")
	private String valueDesc;

	@ApiModelProperty(value = "单位文案")
	private String unitDesc;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		CouponInfoVo that = (CouponInfoVo) o;
		return Objects.equals(getId(), that.getId()) && Objects.equals(getCouponUseId(), that.getCouponUseId()) && Objects.equals(getAvailable(), that.getAvailable()) && Objects.equals(getName(), that.getName()) && Objects.equals(getStartAt(), that.getStartAt()) && Objects.equals(getEndAt(), that.getEndAt()) && Objects.equals(getCondition(), that.getCondition()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getReason(), that.getReason()) && Objects.equals(getValue(), that.getValue()) && Objects.equals(getValueDesc(), that.getValueDesc()) && Objects.equals(getUnitDesc(), that.getUnitDesc());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getId(), getCouponUseId(), getAvailable(), getName(), getStartAt(), getEndAt(), getCondition(), getDescription(), getReason(), getValue(), getValueDesc(), getUnitDesc());
	}
}