package com.lwl.ggkt.model.live;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lwl.ggkt.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

@Data
@ApiModel(description = "CourseDescription")
@TableName("live_course_description")
public class LiveCourseDescription extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程ID")
	@TableField("live_course_id")
	private Long liveCourseId;

	@ApiModelProperty(value = "课程简介")
	@TableField("description")
	private String description;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		LiveCourseDescription that = (LiveCourseDescription) o;
		return Objects.equals(getLiveCourseId(), that.getLiveCourseId()) && Objects.equals(getDescription(), that.getDescription());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getLiveCourseId(), getDescription());
	}
}