package com.lwl.ggkt.wechat.service;

import java.util.Map;

/**
 * @author user-lwl
 * @createDate 2022/12/10 10:21
 */
public interface MessageService {
    /**
     * 接收微信服务器发送来的消息
     * @param param param
     * @return 消息
     */
    String receiveMessage(Map<String, String> param);

    /**
     * 模板消息
     * @param orderId orderId
     */
    void pushPayMessage(long orderId);
}
