package com.lwl.ggkt.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.live.mapper.LiveCourseGoodsMapper;
import com.lwl.ggkt.live.service.LiveCourseGoodsService;
import com.lwl.ggkt.model.live.LiveCourseGoods;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author user-lwl
* @description 针对表【live_course_goods(直播课程关联推荐表)】的数据库操作Service实现
* @createDate 2022-12-17 10:31:22
*/
@Service
public class LiveCourseGoodsServiceImpl extends ServiceImpl<LiveCourseGoodsMapper, LiveCourseGoods>
    implements LiveCourseGoodsService {
    @Resource
    private LiveCourseGoodsMapper liveCourseGoodsMapper;

    /**
     * 查询直播课程商品列表
     * @param id 课程id
     * @return 商品列表
     */
    @Override
    public List<LiveCourseGoods> getGoodsListCourseId(Long id) {
        LambdaQueryWrapper<LiveCourseGoods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LiveCourseGoods::getLiveCourseId, id);
        return liveCourseGoodsMapper.selectList(queryWrapper);
    }
}




