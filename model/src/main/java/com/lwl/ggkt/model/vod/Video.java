package com.lwl.ggkt.model.vod;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lwl.ggkt.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

@Data
@ApiModel(description = "Video")
@TableName("video")
public class Video extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程ID")
	@TableField("course_id")
	private Long courseId;

	@ApiModelProperty(value = "章节ID")
	@TableField("chapter_id")
	private Long chapterId;

	@ApiModelProperty(value = "节点名称")
	@TableField("title")
	private String title;

	@ApiModelProperty(value = "云端视频资源")
	@TableField("video_source_id")
	private String videoSourceId;

	@ApiModelProperty(value = "原始文件名称")
	@TableField("video_original_name")
	private String videoOriginalName;

	@ApiModelProperty(value = "排序字段")
	@TableField("sort")
	private Integer sort;

	@ApiModelProperty(value = "播放次数")
	@TableField("play_count")
	private Long playCount;

	@ApiModelProperty(value = "是否可以试听：0收费 1免费")
	@TableField("is_free")
	private Integer isFree;

	@ApiModelProperty(value = "视频时长（秒）")
	@TableField("duration")
	private Float duration;

	@ApiModelProperty(value = "视频源文件大小（字节）")
	@TableField("size")
	private Long size;

	@ApiModelProperty(value = "乐观锁")
	@TableField("version")
	private Long version;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Video video = (Video) o;
		return Objects.equals(getCourseId(), video.getCourseId()) && Objects.equals(getChapterId(), video.getChapterId()) && Objects.equals(getTitle(), video.getTitle()) && Objects.equals(getVideoSourceId(), video.getVideoSourceId()) && Objects.equals(getVideoOriginalName(), video.getVideoOriginalName()) && Objects.equals(getSort(), video.getSort()) && Objects.equals(getPlayCount(), video.getPlayCount()) && Objects.equals(getIsFree(), video.getIsFree()) && Objects.equals(getDuration(), video.getDuration()) && Objects.equals(getSize(), video.getSize()) && Objects.equals(getVersion(), video.getVersion()) && Objects.equals(getStatus(), video.getStatus());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getCourseId(), getChapterId(), getTitle(), getVideoSourceId(), getVideoOriginalName(), getSort(), getPlayCount(), getIsFree(), getDuration(), getSize(), getVersion(), getStatus());
	}
}