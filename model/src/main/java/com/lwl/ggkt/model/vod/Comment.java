package com.lwl.ggkt.model.vod;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lwl.ggkt.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

@Data
@ApiModel(description = "Comment")
@TableName("comment")
public class Comment extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程id")
	@TableField("course_id")
	private Long courseId;

	@ApiModelProperty(value = "讲师id")
	@TableField("teacher_id")
	private Long teacherId;

	@ApiModelProperty(value = "会员id")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "会员昵称")
	@TableField("nickname")
	private String nickname;

	@ApiModelProperty(value = "会员头像")
	@TableField("avatar")
	private String avatar;

	@ApiModelProperty(value = "评论内容")
	@TableField("content")
	private String content;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Comment comment = (Comment) o;
		return Objects.equals(getCourseId(), comment.getCourseId()) && Objects.equals(getTeacherId(), comment.getTeacherId()) && Objects.equals(getUserId(), comment.getUserId()) && Objects.equals(getNickname(), comment.getNickname()) && Objects.equals(getAvatar(), comment.getAvatar()) && Objects.equals(getContent(), comment.getContent());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getCourseId(), getTeacherId(), getUserId(), getNickname(), getAvatar(), getContent());
	}
}