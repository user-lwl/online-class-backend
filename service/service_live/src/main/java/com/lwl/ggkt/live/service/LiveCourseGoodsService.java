package com.lwl.ggkt.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lwl.ggkt.model.live.LiveCourseGoods;

import java.util.List;

/**
* @author user-lwl
* @description 针对表【live_course_goods(直播课程关联推荐表)】的数据库操作Service
* @createDate 2022-12-17 10:31:22
*/
public interface LiveCourseGoodsService extends IService<LiveCourseGoods> {

    /**
     * 查询直播课程商品列表
     * @param id 课程id
     * @return 商品列表
     */
    List<LiveCourseGoods> getGoodsListCourseId(Long id);
}
