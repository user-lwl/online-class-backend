package com.lwl.ggkt.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.activity.mapper.CouponInfoMapper;
import com.lwl.ggkt.activity.service.CouponInfoService;
import com.lwl.ggkt.activity.service.CouponUseService;
import com.lwl.ggkt.client.user.UserInfoFeignClient;
import com.lwl.ggkt.model.activity.CouponInfo;
import com.lwl.ggkt.model.activity.CouponUse;
import com.lwl.ggkt.model.user.UserInfo;
import com.lwl.ggkt.vo.activity.CouponUseQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author user-lwl
* @description 针对表【coupon_info(优惠券信息)】的数据库操作Service实现
* @createDate 2022-12-04 10:27:15
*/
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo>
    implements CouponInfoService {
    @Resource
    private CouponUseService couponUseService;

    @Resource
    private UserInfoFeignClient userInfoFeignClient;

    /**
     * 获取分页列表
     * @param pageParam pageParam
     * @param couponUseQueryVo couponUseQueryVo
     * @return 分页列表
     */
    @Override
    public IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo) {
        // 取条件
        Long couponId = couponUseQueryVo.getCouponId();
        String couponStatus = couponUseQueryVo.getCouponStatus();
        String getTimeBegin = couponUseQueryVo.getGetTimeBegin();
        String getTimeEnd = couponUseQueryVo.getGetTimeEnd();
        //封装条件
        QueryWrapper<CouponUse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(couponId)) {
            wrapper.eq("coupon_id",couponId);
        }
        if(!StringUtils.isEmpty(couponStatus)) {
            wrapper.eq("coupon_status",couponStatus);
        }
        if(!StringUtils.isEmpty(getTimeBegin)) {
            wrapper.ge("get_time",getTimeBegin);
        }
        if(!StringUtils.isEmpty(getTimeEnd)) {
            wrapper.le("get_time",getTimeEnd);
        }
        //调用方法查询
        IPage<CouponUse> page = couponUseService.page(pageParam, wrapper);
        //封装用户昵称和手机号
        List<CouponUse> couponUseList = page.getRecords();
        couponUseList.forEach(this::getUserInfoByCouponUse);
        return page;
    }

    /**
     * 更新优惠券使用状态
     * @param couponUseId couponUseId
     * @param orderId orderId
     */
    @Override
    public void updateCouponInfoUseStatus(Long couponUseId, Long orderId) {
        CouponUse couponUse = new CouponUse();
        couponUse.setId(couponUseId);
        couponUse.setOrderId(orderId);
        couponUse.setCouponStatus("1");
        couponUse.setUsingTime(new Date());
        couponUseService.updateById(couponUse);
    }

    /**
     * 封装用户昵称和手机号（远程调用）
     *
     * @param couponUse couponUse
     */
    private void getUserInfoByCouponUse(CouponUse couponUse) {
        Long userId = couponUse.getUserId();
        if(!StringUtils.isEmpty(userId)) {
            UserInfo userInfo = userInfoFeignClient.getById(userId);
            if(userInfo != null) {
                couponUse.getParam().put("nickName", userInfo.getNickName());
                couponUse.getParam().put("phone", userInfo.getPhone());
            }
        }
    }
}




