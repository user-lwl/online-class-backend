package com.lwl.ggkt.model.vod;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lwl.ggkt.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

@Data
@ApiModel(description = "Chapter")
@TableName("chapter")
public class Chapter extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "课程ID")
	@TableField("course_id")
	private Long courseId;

	@ApiModelProperty(value = "章节名称")
	@TableField("title")
	private String title;

	@ApiModelProperty(value = "显示排序")
	@TableField("sort")
	private Integer sort;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Chapter chapter = (Chapter) o;
		return Objects.equals(getCourseId(), chapter.getCourseId()) && Objects.equals(getTitle(), chapter.getTitle()) && Objects.equals(getSort(), chapter.getSort());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getCourseId(), getTitle(), getSort());
	}
}