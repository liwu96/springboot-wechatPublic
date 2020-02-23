package com.richard.project.conroller;


import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.richard.project.service.IWxPublicService;
import com.richard.project.service.impl.WxPublicService;
import com.richard.project.utils.SignUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author sun
 * @version date：2019年5月22日 下午2:53:46
 * @description ：
 */
@Controller
@RequestMapping("/wechat")
public class WxPublicMainController {
    //private static Logger logger = Logger.getLogger(WxPublicMainController.class);

    @Resource
    public WxPublicService wxPublicService;

    @RequestMapping(value = "testWx", method = RequestMethod.GET)
    /**
    * @Description 确认请求来源于微信服务器
    * @Author  richard
    * @Date   2020/2/23 17:58
    * @Param  [request, response, signature, timestamp, nonce, echostr]
    * @Return      void
    * @Exception
    *
    */
    public void doGet(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "signature", required = true) String signature,
            @RequestParam(value = "timestamp", required = true) String timestamp,
            @RequestParam(value = "nonce", required = true) String nonce,
            @RequestParam(value = "echostr", required = true) String echostr) {
        try {
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                PrintWriter out = response.getWriter();
                out.print(echostr);
                out.close();
            } else {
//                logger.info("这里存在非法请求！");
                System.out.println("非法请求");
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    @RequestMapping(value = "testWx", method = RequestMethod.POST)
    /**
    * @Description 方法用于接收并处理微信服务端消息
    * @Author  richard
    * @Date   2020/2/23 17:59
    * @Param  []
    * @Return      void
    * @Exception
    *
    */
    public void DoPost( HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
        System.out.println("这是post方法！");
        //  消息的接受处理相应
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");

        //调用业务层处理消息
        String respMeg = "测试";
        respMeg = wxPublicService.mainService(request,respMeg);
        //相应消息
        PrintWriter out = response.getWriter();
        try {
            out = response.getWriter();
            out.print(respMeg);
        } catch (IOException e) {
            e.printStackTrace();
            //logger.error(e.getMessage(),e);
        } finally {
            out.close();
            out = null;
        }

    }
}