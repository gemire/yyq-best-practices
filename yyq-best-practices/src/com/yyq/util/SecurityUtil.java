package com.yyq.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;

/**
 * 安全工具类，进行RSA加密解密相关操作
 * @author YYQ
 */
public class SecurityUtil {
	/**
	 * 在C盘下生成密钥库和证书
	 */
	public static void initRSA() {
		try{
		KeyStore keyStore = KeyStore.getInstance("PKCS12", new BouncyCastleProvider());
		keyStore.load(null, null);
		KeyPair kp=generateRSAKeyPair();
		
//		Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", new BouncyCastleProvider());
//		cipher.init(Cipher.ENCRYPT_MODE, kp.getPublic());
//		byte[] enText = cipher.doFinal("hello".getBytes("UTF-8"));
//		cipher.init(Cipher.DECRYPT_MODE, kp.getPrivate());
//		byte[] deText = cipher.doFinal(enText);
//		String text=new String(deText,"UTF-8");
//		System.out.println(text);
		
		X509Certificate cert = generateV3Certificate(kp);
		FileOutputStream os = new FileOutputStream("C:/rsaCer.cer");
		os.write(cert.getEncoded());
		os.flush();
		os.close();
		
		os = new FileOutputStream("C:/rsaKeyStore.keystore");
		
		PrivateKey pk = kp.getPrivate();
		keyStore.setKeyEntry("mykey", pk, "123456".toCharArray(), new Certificate[]{cert});
		keyStore.store(os, "123456".toCharArray());
		keyStore.store(new FileOutputStream("C:/keystore.p12"), "123456".toCharArray());
		/*
		Enumeration en = keyStore.aliases();
		while (en.hasMoreElements())
		{
			String alias = (String)en.nextElement();
			System.out.println("found " + alias + ", isCertificate? " + keyStore.isCertificateEntry(alias));
		}
		*/
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 生成RSA密钥对
	 * @return
	 * @throws Exception
	 */
	public static KeyPair generateRSAKeyPair() throws Exception{
		KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", new BouncyCastleProvider());

		kpGen.initialize(1024, new SecureRandom());

		return kpGen.generateKeyPair();
	}
	/**
	 * 生成RSA证书
	 * @param pair
	 * @return
	 * @throws Exception
	 */
	public static X509Certificate generateV3Certificate(KeyPair pair)throws Exception{
		X509V3CertificateGenerator  certGen = new X509V3CertificateGenerator();

		certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
		//CN:姓氏、名字  OU:组织单位名称  O:组织名称  L:城市、区域  C:国家代码
		certGen.setIssuerDN(new X500Principal("CN=YYQ, OU=XMU, O=XMU, L=XM, C=CN"));
		//证书有效期起
		certGen.setNotBefore(new Date(System.currentTimeMillis()));
		//证书有效期止
		certGen.setNotAfter(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 365)));
		certGen.setSubjectDN(new X500Principal("CN=YYQ"));
		certGen.setPublicKey(pair.getPublic());
		System.out.println(pair.getPublic().toString());
		certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");

		return certGen.generateX509Certificate(pair.getPrivate());
	}
	/**
	 * RSA解密（RSA加密在C#中进行）
	 * @param enText
	 * @return
	 * @throws Exception
	 */
	public static byte[] deCrypt(byte[] enText) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", new BouncyCastleProvider());
		//cipher.init(Cipher.DECRYPT_MODE, privKey);
		byte[] deText = cipher.doFinal(enText);
		return deText;
	}
	public static void main(String[] args) throws Exception{
		//initRSA();
		// create the keys
		
		InputStream in = new FileInputStream("C:/rsaCer.cer");
		CertificateFactory cf=CertificateFactory.getInstance("x509");
		
		Certificate cert = cf.generateCertificate(in);
		PublicKey key=cert.getPublicKey();
		System.out.println(key);
		Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", new BouncyCastleProvider());
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] b = cipher.doFinal("yyq".getBytes());
		System.out.println(new String(b,"UTF-8"));
		//?^U?????A?2??????v?O??G?}/>|?MA5??z???,?8)t?>??$?<?EMv???QhH?3<?p?hgq#???qT?B)_????8??#3???~Uk?z?7?eB
	}
}
