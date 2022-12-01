package com.lwl.ggkt.vo.live;

import com.lwl.ggkt.model.live.LiveCourseConfig;
import com.lwl.ggkt.model.live.LiveCourseGoods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@ApiModel(description = "LiveCourseConfig")
public class LiveCourseConfigVo extends LiveCourseConfig {

	@ApiModelProperty(value = "商品列表")
	private List<LiveCourseGoods> liveCourseGoodsList;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		LiveCourseConfigVo that = (LiveCourseConfigVo) o;
		return Objects.equals(getLiveCourseGoodsList(), that.getLiveCourseGoodsList());
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getLiveCourseGoodsList());
	}
}