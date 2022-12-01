package com.lwl.ggkt.model.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lwl.ggkt.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

@Data
@ApiModel(description = "UserLoginLog")
@TableName("user_login_log")
public class UserLoginLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "登录ip")
	@TableField("ip")
	private String ip;

	@ApiModelProperty(value = "登录城市")
	@TableField("city")
	private String city;

	@ApiModelProperty(value = "登录类型【0-web，1-移动】")
	@TableField("type")
	private Boolean type;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		UserLoginLog that = (UserLoginLog) o;
		return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getIp(), that.getIp()) && Objects.equals(getCity(), that.getCity()) && Objects.equals(getType(), that.getType());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getUserId(), getIp(), getCity(), getType());
	}
}