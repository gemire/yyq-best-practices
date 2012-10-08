package com.yyq.util;

import java.security.PrivateKey;

/**
 * 存储RSA公钥密钥的工具类
 * @author YYQ
 */
public class RSAUtil {
	//登陆页面上使用的RSA加密公钥
	public static String publicKey;
	//后台使用的RSA解密密钥
	public static PrivateKey privateKey;
	public static String getPublicKey(){
//		System.out.println(publicKey);
//		System.out.println(privateKey);
		return publicKey;
	}
}
