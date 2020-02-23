package com.richard.project.conroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.richard.project.utils.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * 微信公众号API
 * @author youcong
 * @date 2019-6-02
 */
@RestController
@RequestMapping("/wx_public_api")
//@Api(tags = { "微信公众号api" }, description = "微信公众号api")
public class WxPublicApiController extends AbstractController
{

//    @Autowired
   // private WxService wxService;


    /**
     * 微信公众平台服务器配置验证
     * @param request
     * @param response
     */
    @GetMapping
   // @ApiOperation("微信公众平台服务器配置验证")
    public void validate(HttpServletRequest request, HttpServletResponse response) {
        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，否则接入失败
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                out.print(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());

        } finally {

            out.close();
            out = null;
        }
    }

    /**
     * 关注推送消息
     * @param request
     * @param response
     */
    @PostMapping
   // @ApiOperation("关注推送消息")
    public void about(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        response.setContentType("text/html;charset=UTF-8");

        // 调用核心业务类接收消息、处理消息
        String respMessage = "";
               // wxService.newMessageRequest(request);

        // 响应消息
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(respMessage);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        } finally {
            out.close();
            out = null;
        }
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return null;
    }
}

