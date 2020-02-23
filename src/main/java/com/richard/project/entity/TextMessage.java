package com.richard.project.entity;
/**
 * 文本消息
 * @author youcong
 * @create 2019-05-22 21:10
 */
public class TextMessage extends BaseMessage{
    // 消息内容
    private String Content;
    
    
	public String getContent() {
        return Content;
    }
    public void setContent(String content) {
        Content = content;
    }
    
}
