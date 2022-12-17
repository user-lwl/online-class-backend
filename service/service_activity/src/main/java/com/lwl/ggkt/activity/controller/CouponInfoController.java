package com.lwl.ggkt.activity.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwl.ggkt.activity.service.CouponInfoService;
import com.lwl.ggkt.model.activity.CouponInfo;
import com.lwl.ggkt.model.activity.CouponUse;
import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.vo.activity.CouponUseQueryVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author user-lwl
 * @createDate 2022/12/4 10:30
 */
@RequestMapping("/admin/activity/couponInfo")
@RestController
public class CouponInfoController {
    @Resource
    private CouponInfoService couponInfoService;

    /**
     * 获取优惠券
     * @param id 优惠券id
     * @return 优惠券信息
     */
    @ApiOperation(value = "获取优惠券")
    @GetMapping("/get/{id}")
    public Result<CouponInfo> get(@PathVariable String id) {
        CouponInfo couponInfo = couponInfoService.getById(id);
        return Result.ok(couponInfo);
    }

    /**
     * 新增优惠券
     * @param couponInfo 优惠券信息
     * @return 是否成功
     */
    @ApiOperation(value = "新增优惠券")
    @PostMapping("/save")
    public Result<Object> save(@RequestBody CouponInfo couponInfo) {
        couponInfoService.save(couponInfo);
        return Result.ok();
    }

    /**
     * 修改优惠券
     * @param couponInfo 优惠券信息
     * @return 是否成功
     */
    @ApiOperation(value = "修改优惠券")
    @PutMapping("/update")
    public Result<Object> updateById(@RequestBody CouponInfo couponInfo) {
        couponInfoService.updateById(couponInfo);
        return Result.ok();
    }

    /**
     * 删除优惠券
     * @param id 优惠券id
     * @return 是否成功
     */
    @ApiOperation(value = "删除优惠券")
    @DeleteMapping("/remove/{id}")
    public Result<Object> remove(@PathVariable String id) {
        couponInfoService.removeById(id);
        return Result.ok();
    }

    /**
     * 根据id列表批量删除优惠券
     * @param idList id列表
     * @return 是否成功
     */
    @ApiOperation(value="根据id列表删除优惠券")
    @DeleteMapping("batchRemove")
    public Result<Object> batchRemove(@RequestBody List<String> idList){
        couponInfoService.removeByIds(idList);
        return Result.ok();
    }

    /**
     * 获取分页列表
     * @param page 当前页数
     * @param limit 每页记录数
     * @return 分页列表
     */
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result<IPage<CouponInfo>> index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<CouponInfo> pageParam = new Page<>(page, limit);
        IPage<CouponInfo> pageModel = couponInfoService.page(pageParam);
        return Result.ok(pageModel);
    }

    /**
     * 获取分页列表
     * @param page 当前页
     * @param limit 每页记录数
     * @param couponUseQueryVo couponUseQueryVo
     * @return 分页列表
     */
    @ApiOperation(value = "获取分页列表")
    @GetMapping("couponUse/{page}/{limit}")
    public Result<IPage<CouponUse>> index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "couponUseVo", value = "查询对象")
            CouponUseQueryVo couponUseQueryVo) {
        Page<CouponUse> pageParam = new Page<>(page, limit);
        IPage<CouponUse> pageModel = couponInfoService.selectCouponUsePage(pageParam, couponUseQueryVo);
        return Result.ok(pageModel);
    }
}
