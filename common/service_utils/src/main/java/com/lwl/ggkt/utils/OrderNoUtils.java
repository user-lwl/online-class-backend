package com.lwl.ggkt.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 订单号工具类
 *
 * @author qy
 * @since 1.0
 */
public class OrderNoUtils {

    /**
     * 获取订单号
     * @return 订单号
     */
    public static String getOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        StringBuilder result = new StringBuilder();
        Random random;
        try {
            random = SecureRandom.getInstanceStrong();
            for (int i = 0; i < 3; i++) {
                result.append(random.nextInt(10));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return newDate + result;
    }

}
