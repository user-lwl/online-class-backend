package com.lwl.ggkt.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.client.activity.CouponInfoFeignClient;
import com.lwl.ggkt.client.course.CourseFeignClient;
import com.lwl.ggkt.client.user.UserInfoFeignClient;
import com.lwl.ggkt.exception.GgktException;
import com.lwl.ggkt.model.activity.CouponInfo;
import com.lwl.ggkt.model.order.OrderDetail;
import com.lwl.ggkt.model.order.OrderInfo;
import com.lwl.ggkt.model.user.UserInfo;
import com.lwl.ggkt.model.vod.Course;
import com.lwl.ggkt.order.mapper.OrderInfoMapper;
import com.lwl.ggkt.order.service.OrderDetailService;
import com.lwl.ggkt.order.service.OrderInfoService;
import com.lwl.ggkt.utils.AuthContextHolder;
import com.lwl.ggkt.utils.OrderNoUtils;
import com.lwl.ggkt.vo.order.OrderFormVo;
import com.lwl.ggkt.vo.order.OrderInfoQueryVo;
import com.lwl.ggkt.vo.order.OrderInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author user-lwl
* @description 针对表【order_info(订单表 订单表)】的数据库操作Service实现
* @createDate 2022-12-03 20:55:06
*/
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo>
    implements OrderInfoService {
    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private OrderDetailService orderDetailService;

    @Resource
    private CourseFeignClient courseFeignClient;

    @Resource
    private UserInfoFeignClient userInfoFeignClient;

    @Resource
    private CouponInfoFeignClient couponInfoFeignClient;

    /**
     * 分页查询订单列表
     * @param pageParam pageParam
     * @param orderInfoQueryVo orderInfoQueryVo
     * @return 订单列表
     */
    @Override
    public Map<String, Object> selectOrderInfoPage(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo) {
        // 条件
        Long userId = orderInfoQueryVo.getUserId();
        String outTradeNo = orderInfoQueryVo.getOutTradeNo();
        String phone = orderInfoQueryVo.getPhone();
        String createTimeBegin = orderInfoQueryVo.getCreateTimeBegin();
        String createTimeEnd = orderInfoQueryVo.getCreateTimeEnd();
        Integer orderStatus = orderInfoQueryVo.getOrderStatus();
        // 判空 非空 封装
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userId)) {
            queryWrapper.eq("user_id", userId);
        }
        if(!StringUtils.isEmpty(outTradeNo)) {
            queryWrapper.eq("out_trade_no",outTradeNo);
        }
        if(!StringUtils.isEmpty(phone)) {
            queryWrapper.eq("phone",phone);
        }
        if(!StringUtils.isEmpty(createTimeBegin)) {
            queryWrapper.ge("create_time",createTimeBegin);
        }
        if(!StringUtils.isEmpty(createTimeEnd)) {
            queryWrapper.le("create_time",createTimeEnd);
        }
        if(!StringUtils.isEmpty(orderStatus)) {
            queryWrapper.eq("order_status",orderStatus);
        }
        // 分页查询
        Page<OrderInfo> pages = orderInfoMapper.selectPage(pageParam, queryWrapper);
        long totalCount = pages.getTotal();
        long pagesCount = pages.getPages();
        List<OrderInfo> records = pages.getRecords();
        // 封装详情
        records.forEach(this::getOrderDetail);
        // map 返回
        Map<String, Object> map = new HashMap<>();
        map.put("total", totalCount);
        map.put("pageCount", pagesCount);
        map.put("records", records);
        return map;
    }

    /**
     * 新增点播课程订单
     * @param orderFormVo orderFormVo
     * @return 订单id
     */
    @Override
    public Long submitOrder(OrderFormVo orderFormVo) {
        Long userId = AuthContextHolder.getUserId();
        Long courseId = orderFormVo.getCourseId();
        Long couponId = orderFormVo.getCouponId();
        //查询当前用户是否已有当前课程的订单
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getCourseId, courseId);
        queryWrapper.eq(OrderDetail::getUserId, userId);
        OrderDetail orderDetailExist = orderDetailService.getOne(queryWrapper);
        if(orderDetailExist != null){
            return orderDetailExist.getId(); //如果订单已存在，则直接返回订单id
        }

        //查询课程信息
        Course course = courseFeignClient.getById(courseId);
        if (course == null) {
            throw new GgktException(20001, "课程不存在");
        }

        //查询用户信息
        UserInfo userInfo = userInfoFeignClient.getById(userId);
        if (userInfo == null) {
            throw new GgktException(20001, "用户不存在");
        }

        //优惠券金额
        BigDecimal couponReduce = new BigDecimal(0);
        if(null != couponId) {
            CouponInfo couponInfo = couponInfoFeignClient.getById(couponId);
            couponReduce = couponInfo.getAmount();
        }

        //创建订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(userId);
        orderInfo.setNickName(userInfo.getNickName());
        orderInfo.setPhone(userInfo.getPhone());
        orderInfo.setProvince(userInfo.getProvince());
        orderInfo.setOriginAmount(course.getPrice());
        orderInfo.setCouponReduce(couponReduce);
        orderInfo.setFinalAmount(orderInfo.getOriginAmount().subtract(orderInfo.getCouponReduce()));
        orderInfo.setOutTradeNo(OrderNoUtils.getOrderNo());
        orderInfo.setTradeBody(course.getTitle());
        orderInfo.setOrderStatus("0");
        this.save(orderInfo);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderInfo.getId());
        orderDetail.setUserId(userId);
        orderDetail.setCourseId(courseId);
        orderDetail.setCourseName(course.getTitle());
        orderDetail.setCover(course.getCover());
        orderDetail.setOriginAmount(course.getPrice());
        orderDetail.setCouponReduce(new BigDecimal(0));
        orderDetail.setFinalAmount(orderDetail.getOriginAmount().subtract(orderDetail.getCouponReduce()));
        orderDetailService.save(orderDetail);

        //更新优惠券状态
        if(null != orderFormVo.getCouponUseId()) {
            couponInfoFeignClient.updateCouponInfoUseStatus(orderFormVo.getCouponUseId(), orderInfo.getId());
        }
        return orderInfo.getId();
    }

    /**
     * 根据订单号查询订单信息
     * @param id 订单id
     * @return 订单信息
     */
    @Override
    public OrderInfoVo getOrderInfoVoById(Long id) {
        OrderInfo orderInfo = orderInfoMapper.selectById(id);
        OrderDetail orderDetail = orderDetailService.getById(id);
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        BeanUtils.copyProperties(orderInfo, orderInfoVo);
        orderInfoVo.setCourseId(orderDetail.getCourseId());
        orderInfoVo.setCourseName(orderDetail.getCourseName());
        return orderInfoVo;
    }

    /**
     * 更新订单状态
     * @param outTradeNo outTradeNo
     */
    @Override
    public void updateOrderStatus(String outTradeNo) {
        //根据out_trade_no查询订单
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getOutTradeNo, outTradeNo);
        OrderInfo orderInfo = baseMapper.selectOne(wrapper);
        //更新订单状态 1 已经支付
        orderInfo.setOrderStatus("1");
        baseMapper.updateById(orderInfo);
    }

    /**
     * 查询订单详情数据
     *
     * @param orderInfo 订单详情
     */
    private void getOrderDetail(OrderInfo orderInfo) {
        Long id = orderInfo.getId();
        OrderDetail orderDetail = orderDetailService.getById(id);
        if (orderDetail != null) {
            String courseName = orderDetail.getCourseName();
            orderDetail.getParam().put("courseName", courseName);
        }
    }
}




