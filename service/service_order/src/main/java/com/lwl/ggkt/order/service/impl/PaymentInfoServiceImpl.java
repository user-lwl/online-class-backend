package com.lwl.ggkt.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwl.ggkt.model.order.PaymentInfo;
import com.lwl.ggkt.order.mapper.PaymentInfoMapper;
import com.lwl.ggkt.order.service.PaymentInfoService;
import org.springframework.stereotype.Service;

/**
* @author user-lwl
* @description 针对表【payment_info(支付信息表)】的数据库操作Service实现
* @createDate 2022-12-03 20:55:06
*/
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo>
    implements PaymentInfoService {

}




