package com.richard.project.service.impl;

import com.richard.project.entity.Article;
import com.richard.project.entity.NewsMessage;
import com.richard.project.entity.TextMessage;
import com.richard.project.service.IWxPublicService;
import com.richard.project.utils.MessageUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: richard
 * @Date: 2020/2/23 18:06
 * @Description:
 */
@Service
public class WxPublicService implements IWxPublicService {

    public String mainService(HttpServletRequest request, String str) {
        String respMessage = null;
        try {
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.xmlToMap(request);
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 消息内容
            String content = requestMap.get("Content");

            String resultContent = "";
            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                if ("Java".equals(content)) {
                    resultContent = "Java相关框架资料及其基础资料、进阶资料、测试资料之分享:\r\n"
                            + "链接：https://pan.baidu.com/s/1JlguUNvm3ZywswKo5tlyLw \n" +
                    "提取码：b5pa";
                }else if ("软件".equals(content)) {
                    resultContent = "软件:\r\n" + "链接：https://pan.baidu.com/s/1fQVXJu16U43MDarw37vWKA \n" +
                            "提取码：saa1\r\n";
                }else if ("电子书".equals(content)) {
                    resultContent = "软件:\r\n" + "链接：https://pan.baidu.com/s/1JlguUNvm3ZywswKo5tlyLw \n" +
                            "提取码：b5pa";
                } else {

                    resultContent = "欢迎关注我的微信公众号\r\n"
                            + "我的Github平台为:\r\n" + "https://github.com/liwu96\r\n"
                            + "1.回复'软件'可获取我收集的技术相关软件\r\n"
                            + "2.回复'电子书'可获取我收集的技术相关电子书\r\n";
                }
                TextMessage text = new TextMessage();
                text.setContent(resultContent);
                text.setToUserName(fromUserName);
                text.setFromUserName(toUserName);
                text.setCreateTime(new Date().getTime());
                text.setMsgType(msgType);
                respMessage = MessageUtil.textMessageToXml(text);

            }else if(msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_NEWS)) {
                if(content.equals("1")) {
                    NewsMessage imageMsg = new NewsMessage();
                    imageMsg.setFromUserName(fromUserName);

                    imageMsg.setToUserName(toUserName);

                    imageMsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);

                    List<Article> articles = new ArrayList<Article>();

                    Article article = new Article();
                    article.setTitle("Java相关框架资料及其基础资料、进阶资料、测试资料之分享");
                    article.setDescription("个人说明：只为分享，不为其他，愿所有的程序员们在编程的世界自由翱翔吧!\r\n" +
                            "\r\n" +
                            "在我看来，只有不断实战，不断学习，不断积累，不断归纳总结，形成自己的核心竞争力，方能在未来竞争中脱颖而出！");
                    article.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560050035&di=1fb524ff28f69db1766ab9a1d53cad86&imgtype=jpg&er=1&src=http%3A%2F%2Fpic23.nipic.com%2F20120918%2F461037_195425463301_2.jpg");
                    article.setUrl("https://mp.weixin.qq.com/s?__biz=MzUxODk0ODQ3Ng==&mid=2247483736&idx=1&sn=f56e18620bb1a0ec9a8f49657a788d0e&chksm=f980524bcef7db5d28a32826df3b42e418faa40c16699ac72f993d449db0d7ebb365d4792d3d&token=276204794&lang=zh_CN#rd");

                    articles.add(article);

                    imageMsg.setArticles(articles);
                    imageMsg.setArticleCount(articles.size());

                    respMessage = MessageUtil.newsMessageToXml(imageMsg);
                }


            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {

                String eventType = requestMap.get("Event");// 事件类型
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    // 文本消息
                    TextMessage text = new TextMessage();
                    resultContent = "欢迎关注我的微信公众号\r\n"
                            + "我的Github平台为:\r\n" + "https://github.com/liwu96\r\n"
                            + "1.回复'软件'可获取我收集的技术相关软件\r\n"
                            + "2.回复'电子书'可获取我收集的技术相关电子书\r\n";
                    text.setContent(resultContent);
                    text.setToUserName(fromUserName);
                    text.setFromUserName(toUserName);
                    text.setCreateTime(new Date().getTime());
                    text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                    respMessage = MessageUtil.textMessageToXml(text);
                }
                // 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消订阅

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;



    }
}
