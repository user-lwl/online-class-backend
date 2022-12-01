package com.lwl.ggkt.model.vod;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lwl.ggkt.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.Objects;

@Data
@ApiModel(description = "VideoVisitor")
@TableName("video_visitor")
public class VideoVisitor extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程id")
	@TableField("course_id")
	private Long courseId;

	@ApiModelProperty(value = "视频id")
	@TableField("video_id")
	private Long videoId;

	@ApiModelProperty(value = "来访者用户id")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "昵称")
	@TableField("nick_name")
	private String nickName;

	@ApiModelProperty(value = "进入时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("join_time")
	private Date joinTime;

	@ApiModelProperty(value = "离开的时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("leave_time")
	private Date leaveTime;

	@ApiModelProperty(value = "用户停留的时间(单位：秒)")
	@TableField("duration")
	private Long duration;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		VideoVisitor that = (VideoVisitor) o;
		return Objects.equals(getCourseId(), that.getCourseId()) && Objects.equals(getVideoId(), that.getVideoId()) && Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getNickName(), that.getNickName()) && Objects.equals(getJoinTime(), that.getJoinTime()) && Objects.equals(getLeaveTime(), that.getLeaveTime()) && Objects.equals(getDuration(), that.getDuration());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getCourseId(), getVideoId(), getUserId(), getNickName(), getJoinTime(), getLeaveTime(), getDuration());
	}
}