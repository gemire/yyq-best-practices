package com.yyq.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;

import nl.captcha.Captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yyq.context.RequestHelper;

/**
 * 验证码校验类
 * @author YYQ
 */
public class CaptchaFilter extends UsernamePasswordAuthenticationFilter {
	@Autowired
	private RequestHelper requestHelper;
	@Getter@Setter
	private String captchaFailureUrl;
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		//校验验证码
		Captcha captcha=(Captcha)request.getSession().getAttribute("simpleCaptcha");
		String captchafield=request.getParameter("captchafield");
		//System.out.println(captcha);
		if(captcha==null||!captcha.isCorrect(captchafield)){
			try {
				response.sendRedirect(captchaFailureUrl);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//保存nowTime给UserDetailsServiceImpl使用
		requestHelper.setNowTime(request.getParameter("nowTime"));
		
		UsernamePasswordAuthenticationToken authRequest=
			new UsernamePasswordAuthenticationToken(request.getParameter("j_username"), request.getParameter("j_password"));
		
		//进行用户名密码的校验
		return this.getAuthenticationManager().authenticate(authRequest);
	}
}
