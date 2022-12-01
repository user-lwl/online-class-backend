package com.lwl.ggkt.vo.order;

import com.lwl.ggkt.model.order.OrderInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

@Data
public class OrderInfoVo extends OrderInfo {

	@ApiModelProperty(value = "课程id")
	private Long courseId;

	@ApiModelProperty(value = "课程名称")
	private String courseName;

	@ApiModelProperty(value = "课程封面图片路径")
	private String cover;

	@ApiModelProperty(value = "总时长:分钟")
	private Integer durationSum;

	@ApiModelProperty(value = "观看进度总时长:分钟")
	private Integer progressSum;

	@ApiModelProperty(value = "观看进度")
	private Integer progress;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		OrderInfoVo that = (OrderInfoVo) o;
		return Objects.equals(getCourseId(), that.getCourseId()) && Objects.equals(getCourseName(), that.getCourseName()) && Objects.equals(getCover(), that.getCover()) && Objects.equals(getDurationSum(), that.getDurationSum()) && Objects.equals(getProgressSum(), that.getProgressSum()) && Objects.equals(getProgress(), that.getProgress());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getCourseId(), getCourseName(), getCover(), getDurationSum(), getProgressSum(), getProgress());
	}
}

