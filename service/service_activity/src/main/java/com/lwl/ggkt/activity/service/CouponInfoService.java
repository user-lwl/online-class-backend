package com.lwl.ggkt.activity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lwl.ggkt.model.activity.CouponInfo;
import com.lwl.ggkt.model.activity.CouponUse;
import com.lwl.ggkt.vo.activity.CouponUseQueryVo;

/**
* @author user-lwl
* @description 针对表【coupon_info(优惠券信息)】的数据库操作Service
* @createDate 2022-12-04 10:27:15
*/
public interface CouponInfoService extends IService<CouponInfo> {

    /**
     * 获取分页列表
     * @param pageParam pageParam
     * @param couponUseQueryVo couponUseQueryVo
     * @return 分页列表
     */
    IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo);

    /**
     * 更新优惠券使用状态
     * @param couponUseId couponUseId
     * @param orderId orderId
     */
    void updateCouponInfoUseStatus(Long couponUseId, Long orderId);
}
