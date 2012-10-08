package com.yyq.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * JMS与邮件发送工具类
 * @author YYQ
 */
@RemotingDestination(channels={"my-amf"})
public class JmsUtil {
	public static Log log=LogFactory.getLog(JmsUtil.class);
	
	/**
	 * 发送消息
	 * @param textMsg
	 */
	public void sendMessage(String textMsg){
		jmsTemplate.send(creatMessage(textMsg));
	}
	/**
	 * 生成文本消息
	 * @param textMsg
	 * @return
	 */
	private MessageCreator creatMessage(final String textMsg){
		return new MessageCreator(){
			public Message  createMessage(Session session) throws JMSException {
				return session.createTextMessage(textMsg);
			}
		};
	}
	/**
	 * 接收消息
	 * @return
	 */
	public String receiveMessage(){
		try {
			TextMessage textMessage=(TextMessage)jmsTemplate.receive();
			return textMessage.getText();
		} catch (JMSException e) {
			e.printStackTrace();
			return "JMS消息接收出错！";
		}
	}
	/**
	 * 消息监听方法
	 * @param message
	 */
	public void onMessage(String message){
		sendMail(message);
	}
	/**
	 * 使用commons-email发送邮件
	 * @param msg
	 */
	public void sendMail(String msg){
		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.sina.com");//邮件服务器
		email.setCharset("UTF-8");//编码格式
	    
		email.setSubject("用户留言");//标题
		email.setAuthentication("yyqtest@sina.com", "yyqtest");//smtp认证的用户名和密码
		try {
			email.addTo("yyqtest@sina.com","yyq");//收信者
			email.setFrom("yyqtest@sina.com", "yyq");//发信者
			email.setMsg(msg);//内容
			email.send();//发送
			log.info("邮件发送成功！");
		} catch (EmailException e) {
			e.printStackTrace();
		}
		
	}
	
	@Autowired
	@Setter
	private JmsTemplate jmsTemplate;
}
