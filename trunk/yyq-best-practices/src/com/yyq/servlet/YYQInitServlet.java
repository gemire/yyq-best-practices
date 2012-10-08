package com.yyq.servlet;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.yyq.util.RSAUtil;

/**
 * 原先将Spring的ApplicationContext保存在SpringUtil工具类中，现在已经不用,
 * 现在只是初始化RSA非对称密钥对
 * @author YYQ
 */
public class YYQInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public YYQInitServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	/**
	 * 原先将Spring的ApplicationContext保存在SpringUtil工具类中，现在已经不用,
	 * 现在只是初始化RSA非对称密钥对
	 */
	public void init() throws ServletException {
		//原先将Spring的ApplicationContext保存在SpringUtil工具类中，现在已经不用
		//SpringUtil.setContext(WebApplicationContextUtils.getWebApplicationContext(this.getServletContext()));
		
		try{
			KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", new BouncyCastleProvider());
			kpGen.initialize(1024, new SecureRandom());
			KeyPair kp=kpGen.generateKeyPair();
			RSAUtil.privateKey=kp.getPrivate();
			System.out.println("MainTest.privateKey="+RSAUtil.privateKey);
			String test=kp.getPublic().toString();
			int start=test.indexOf(": ");
			int end=test.indexOf("\r", start+2);
			RSAUtil.publicKey=test.substring(start+2, end);
			System.out.println("RSAUtil.publicKey="+RSAUtil.publicKey);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
