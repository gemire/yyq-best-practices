package com.yyq.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.yyq.util.RSAUtil;

import administrator.dao.UserDao;

import nl.captcha.Captcha;

/**
 * 此Filter用于安全校验(此类已停止使用)
 * @author YYQ
 */
public class SSFilter implements Filter {

	private final static Logger log = Logger.getLogger(Filter.class);
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	
	/**
	 * 使用自己的Filter来进行验证码校验，RSA解密后再进行
	 * 客户端IP地址校验、时间戳校验及用户名密码校验
	 */
	public void doFilter(ServletRequest requestS, ServletResponse responseS,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)requestS;
		HttpServletResponse response=(HttpServletResponse)responseS;
		HttpSession session=request.getSession(true);
		
		//校验验证码
		Captcha captcha=(Captcha)session.getAttribute("simpleCaptcha");
		String captchafield=request.getParameter("captchafield");
		//System.out.println(captcha);
		if(captcha==null||!captcha.isCorrect(captchafield)){
			response.sendRedirect("/YYQ/login.jsp?login_error=2");
			return;
		}
		
		String str=request.getParameter("password2");
		System.out.println("客户端javascript RSA加密后的字符串：\n"+str);
		
		String[] s=null;
		try{
		//先将加密的字符串解密出来
		byte[] raw=new BigInteger(str, 16).toByteArray();
		int length=raw.length;
		Cipher cipher = Cipher.getInstance("RSA",new org.bouncycastle.jce.provider.BouncyCastleProvider());
		cipher.init(Cipher.DECRYPT_MODE, RSAUtil.privateKey);
		int blockSize = cipher.getBlockSize();
		ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
		int j = 0;
		while (length - j * blockSize > 0) {
			if((j+1)*blockSize>length){
				bout.write(raw, j*blockSize, length-j*blockSize);
			}else{
				bout.write(cipher.doFinal(raw, j*blockSize, blockSize));
			}
			j++;
		}
		byte[] deText = bout.toByteArray();
		String de=new String(deText);
		StringBuffer sb=new StringBuffer(de);
		de=sb.reverse().toString();
		System.out.println("服务器端Java RSA解密后的字符串：\n"+de);
		
		s=de.split("@");
		if(s.length!=3){
			response.sendRedirect("/YYQ/login.jsp?login_error=1");
			log.error("格式错误！");
			return;
		}
		}catch(Exception e){
			e.printStackTrace();
			response.sendRedirect("/YYQ/login.jsp?login_error=1");
			log.error("RSA解密出错！");
			return;
		}
		
		//校验客户端IP地址
		String ip=s[0];
		if(!ip.equals(request.getRemoteAddr())){
			response.sendRedirect("/YYQ/login.jsp?login_error=1");
			log.info("客户端IP地址校验失败！");
			return;
		}
		
		//校验时间戳
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date=format.parse(s[1]);
			Date date_now=new Date();
			if(date_now.getTime()-date.getTime()>60*1000){
				response.sendRedirect("/YYQ/login.jsp?login_error=1");
				log.info("时间戳校验失败！");
				return;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//校验用户名和密码是否正确
		String username=request.getParameter("j_username");
		String password=new Md5PasswordEncoder().encodePassword(s[2],null);
		UserDao userDao=(UserDao)UserDao.getBeanFromRequest(request, "userDao");
		
		if(userDao.checkUser(username, password)==-1){
			response.sendRedirect("/YYQ/login.jsp?login_error=1");
			log.info("密码校验失败！");
			return;
		}
		System.out.println("ok");
		
		filterChain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
