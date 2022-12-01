package com.lwl.ggkt.model.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

@Data
@ApiModel(description = "MqRepeatRecord")
@TableName("mq_repeat_record")
public class MqRepeatRecord extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "业务编号")
	@TableField("business_no")
	private String businessNo;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		MqRepeatRecord that = (MqRepeatRecord) o;
		return Objects.equals(getBusinessNo(), that.getBusinessNo());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getBusinessNo());
	}
}