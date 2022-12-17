package com.lwl.ggkt.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.wechat.service.MessageService;
import com.lwl.ggkt.wechat.utils.SHA1;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wechat/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    private static final String TOKEN = "ggkt";

    /**
     * 服务器有效性验证
     * @param request request
     * @return String
     */
    @GetMapping
    public String verifyToken(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (this.checkSignature(signature, timestamp, nonce)) {
            System.out.println("ok");
            return echostr;
        }
        return echostr;
    }

    /**
     * 接收微信服务器发送来的消息
     * @param request request
     * @return String
     * @throws Exception Exception
     */
    @PostMapping
    public String receiveMessage(HttpServletRequest request) throws Exception {

//        WxMpXmlMessage wxMpXmlMessage = WxMpXmlMessage.fromXml(request.getInputStream());
//        System.out.println(JSON.toJSONString(wxMpXmlMessage));
        Map<String, String> param = this.parseXml(request);
        return messageService.receiveMessage(param);
    }

    /**
     * 模板消息
     * @return ok
     */
    @GetMapping("/pushPayMessage")
    public Result<Object> pushPayMessage() {
        messageService.pushPayMessage(1L);
        return Result.ok();
    }
    private Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<>();
        InputStream inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        inputStream.close();
        inputStream = null;
        return map;
    }

    private boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] str = new String[]{TOKEN, timestamp, nonce};
        //排序
        Arrays.sort(str);
        //拼接字符串
        StringBuilder buffer = new StringBuilder();
        for (String s : str) {
            buffer.append(s);
        }
        //进行sha1加密
        String temp = SHA1.encode(buffer.toString());
        //与微信提供的signature进行匹对
        return signature.equals(temp);
    }
}