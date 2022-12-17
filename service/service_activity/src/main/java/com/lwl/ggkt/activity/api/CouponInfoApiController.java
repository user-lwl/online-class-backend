package com.lwl.ggkt.activity.api;

import com.lwl.ggkt.activity.service.CouponInfoService;
import com.lwl.ggkt.model.activity.CouponInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "优惠券接口")
@RestController
@RequestMapping("/api/activity/couponInfo")
public class CouponInfoApiController {

	@Resource
	private CouponInfoService couponInfoService;

	/**
	 * 获取优惠券
	 * @param couponId couponId
	 * @return 优惠券信息
	 */
	@ApiOperation(value = "获取优惠券")
	@GetMapping(value = "inner/getById/{couponId}")
	public CouponInfo getById(@PathVariable("couponId") Long couponId) {
		return couponInfoService.getById(couponId);
	}

	/**
	 * 更新优惠券使用状态
	 * @param couponUseId couponUseId
	 * @param orderId orderId
	 * @return 是否成功
	 */
    @ApiOperation(value = "更新优惠券使用状态")
	@GetMapping(value = "inner/updateCouponInfoUseStatus/{couponUseId}/{orderId}")
	public Boolean updateCouponInfoUseStatus(@PathVariable("couponUseId") Long couponUseId, @PathVariable("orderId") Long orderId) {
		couponInfoService.updateCouponInfoUseStatus(couponUseId, orderId);
		return true;
	}
}