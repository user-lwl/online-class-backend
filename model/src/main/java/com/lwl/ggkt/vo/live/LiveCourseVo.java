package com.lwl.ggkt.vo.live;

import com.lwl.ggkt.model.live.LiveCourse;
import com.lwl.ggkt.model.vod.Teacher;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

@Data
public class LiveCourseVo extends LiveCourse {

	@ApiModelProperty(value = "主播老师")
	private Teacher teacher;

	private Integer liveStatus;

	@ApiModelProperty(value = "直播开始时间")
	private String startTimeString;

	@ApiModelProperty(value = "直播结束时间")
	private String endTimeString;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		LiveCourseVo that = (LiveCourseVo) o;
		return Objects.equals(getTeacher(), that.getTeacher()) && Objects.equals(getLiveStatus(), that.getLiveStatus()) && Objects.equals(getStartTimeString(), that.getStartTimeString()) && Objects.equals(getEndTimeString(), that.getEndTimeString());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getTeacher(), getLiveStatus(), getStartTimeString(), getEndTimeString());
	}
}

