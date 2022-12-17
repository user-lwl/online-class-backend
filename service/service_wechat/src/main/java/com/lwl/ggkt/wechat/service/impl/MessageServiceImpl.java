package com.lwl.ggkt.wechat.service.impl;

import com.alibaba.excel.util.CollectionUtils;
import com.lwl.ggkt.client.course.CourseFeignClient;
import com.lwl.ggkt.model.vod.Course;
import com.lwl.ggkt.wechat.service.MessageService;
import lombok.SneakyThrows;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author user-lwl
 * @createDate 2022/12/10 10:22
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private CourseFeignClient courseFeignClient;

    @Resource
    private WxMpService wxMpService;

    public static final String SUCCESS = "success";

    public static final String COLOR = "#272727";

    /**
     * 接收微信服务器发送来的消息
     * @param param param
     * @return 消息
     */
    @Override
    public String receiveMessage(Map<String, String> param) {
        String content;
        try {
            String msgType = param.get("MsgType");
            switch(msgType){
                case "text" :
                    content = this.search(param);
                    break;
                case "event" :
                    String event = param.get("Event");
                    String eventKey = param.get("EventKey");
                    if("subscribe".equals(event)) {//关注公众号
                        content = this.subscribe(param);
                    } else if("unsubscribe".equals(event)) {//取消关注公众号
                        content = this.unsubscribe(param);
                    } else if("CLICK".equals(event) && "aboutUs".equals(eventKey)){
                        content = this.aboutUs(param);
                    } else {
                        content = SUCCESS;
                    }
                    break;
                default:
                    content = SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            content = this.text(param, "请重新输入关键字，没有匹配到相关视频课程").toString();
        }
        return content;
    }

    /**
     * 发送模板消息
     * @param orderId orderId
     */
    @SneakyThrows
    @Override
    public void pushPayMessage(long orderId) {
        String openid = "oepf36SawvvS8Rdqva-Cy4flFFtg";
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openid)//要推送的用户openid
                .templateId("V-x2o4oTIW4rXwGzyM-YprNBQV9XmKxwpk_rQpXeGCE")//模板id
                .url("http://ggkt2.vipgz1.91tunnel.com/#/pay/"+orderId)//点击模板消息要访问的网址
                .build();
        //3,如果是正式版发送消息，，这里需要配置你的信息
        templateMessage.addData(new WxMpTemplateData("first", "亲爱的用户：您有一笔订单支付成功。", COLOR));
        templateMessage.addData(new WxMpTemplateData("keyword1", "1314520", COLOR));
        templateMessage.addData(new WxMpTemplateData("keyword2", "java基础课程", COLOR));
        templateMessage.addData(new WxMpTemplateData("keyword3", "2022-01-11", COLOR));
        templateMessage.addData(new WxMpTemplateData("keyword4", "100", COLOR));
        templateMessage.addData(new WxMpTemplateData("remark", "感谢你购买课程，如有疑问，随时咨询！", COLOR));
        String msg = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        System.out.println(msg);
    }

    /**
     * 关于我们
     * @param param param
     * @return 消息
     */
    private String aboutUs(Map<String, String> param) {
        return this.text(param, "ggkt现开设Java、HTML5前端+全栈、大数据、全链路UI/UE设计、人工智能、大数据运维+Python自动化、Android+HTML5混合开发等多门课程；同时，通过视频分享、谷粒学苑在线课堂、大厂学苑直播课堂等多种方式，满足了全国编程爱好者对多样化学习场景的需求，已经为行业输送了大量IT技术人才。").toString();
    }

    /**
     * 处理关注事件
     * @param param param
     * @return 消息
     */
    private String subscribe(Map<String, String> param) {
        //处理业务
        return this.text(param, "感谢你关注“ggkt”，可以根据关键字搜索您想看的视频教程，如：JAVA基础、Spring boot、大数据等").toString();
    }

    /**
     * 处理取消关注事件
     * @param param param
     * @return 消息
     */
    private String unsubscribe(Map<String, String> param) {
        System.out.println(param);
        //处理业务
        return SUCCESS;
    }

    /**
     * 处理关键字搜索事件
     * 图文消息个数；当用户发送文本、图片、语音、视频、图文、地理位置这六种消息时，开发者只能回复1条图文消息；其余场景最多可回复8条图文消息
     * @param param param
     * @return 消息
     */
    private String search(Map<String, String> param) {
        String fromusername = param.get("FromUserName");
        String tousername = param.get("ToUserName");
        String content = param.get("Content");
        //单位为秒，不是毫秒
        Long createTime = new Date().getTime() / 1000;
        StringBuilder text = new StringBuilder();
        List<Course> courseList = courseFeignClient.findByKeyword(content);
        if(CollectionUtils.isEmpty(courseList)) {
            text = this.text(param, "请重新输入关键字，没有匹配到相关视频课程");
        } else {
            //一次只能返回一个
//            Random random = new Random();
            Random random;
            try {
                random = SecureRandom.getInstanceStrong();
                int num = random.nextInt(courseList.size());
                Course course = courseList.get(num);
                String articles = "<item>" +
                        "<Title><![CDATA[" + course.getTitle() + "]]></Title>" +
                        "<Description><![CDATA[" + course.getTitle() + "]]></Description>" +
                        "<PicUrl><![CDATA[" + course.getCover() + "]]></PicUrl>" +
                        "<Url><![CDATA[http://glkt.atguigu.cn/#/liveInfo/" + course.getId() + "]]></Url>" +
                        "</item>";

                text.append("<xml>");
                text.append("<ToUserName><![CDATA[").append(fromusername).append("]]></ToUserName>");
                text.append("<FromUserName><![CDATA[").append(tousername).append("]]></FromUserName>");
                text.append("<CreateTime><![CDATA[").append(createTime).append("]]></CreateTime>");
                text.append("<MsgType><![CDATA[news]]></MsgType>");
                text.append("<ArticleCount><![CDATA[1]]></ArticleCount>");
                text.append("<Articles>");
                text.append(articles);
                text.append("</Articles>");
                text.append("</xml>");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return text.toString();
    }

    /**
     * 回复文本
     * @param param param
     * @param content content
     * @return 文本
     */
    private StringBuilder text(Map<String, String> param, String content) {
        String fromusername = param.get("FromUserName");
        String tousername = param.get("ToUserName");
        //单位为秒，不是毫秒
        Long createTime = new Date().getTime() / 1000;
        StringBuilder text = new StringBuilder();
        text.append("<xml>");
        text.append("<ToUserName><![CDATA[").append(fromusername).append("]]></ToUserName>");
        text.append("<FromUserName><![CDATA[").append(tousername).append("]]></FromUserName>");
        text.append("<CreateTime><![CDATA[").append(createTime).append("]]></CreateTime>");
        text.append("<MsgType><![CDATA[text]]></MsgType>");
        text.append("<Content><![CDATA[").append(content).append("]]></Content>");
        text.append("</xml>");
        return text;
    }
}
