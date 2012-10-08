package webService;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;

import sale.dao.InsuDao;
import sale.dao.PosDao;
import sale.pojo.Pos;

import administrator.dao.UserDao;

@WebService(endpointInterface = "webService.IWebServiceTest")
public class WebServiceTest implements IWebServiceTest {
	private static final Log log = LogFactory.getLog(WebServiceTest.class);
	
	@Autowired
	private InsuDao insuDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PosDao posDao;
	
	/**
	 * 统计今天销售的保单量
	 */
	public String sumInsuToday() {
		return String.valueOf(insuDao.countInsuToday());
	}
	
	/**
	 * 原字符串格式：客户编号@消费金额@员工账号@员工密码@消费时间
	 * 字符串长度为100，长度不足用#补足
	 */
	public String posRSA(byte[] enText) {
		String text = "error!";
		try{
		InputStream in = new FileInputStream(this.getClass().getResource("/").toString().replace("file:/", "").replaceAll("%20", " ")+"\\rsaKeyStore.keystore");
		KeyStore ks = KeyStore.getInstance("PKCS12", new BouncyCastleProvider());
		ks.load(in, "123456".toCharArray());
		Key sk = ks.getKey("mykey","123456".toCharArray());
		Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", new BouncyCastleProvider());
		cipher.init(Cipher.DECRYPT_MODE, sk);
		byte[] deText=cipher.doFinal(enText);
		text = new String(deText,"UTF-8");
		log.info("解密后的字符串为：" + text);
		text = text.substring(text.length()-100, text.length());
		text = text.replaceAll("#", "");
		String[] context = text.split("@");
		if(context.length!=5) return "error!";
		for(String temp:context){
			log.info(temp);
		}
		
		//验证请求字符串里的用户名密码
		if(userDao.checkUser(context[2], context[3])==-1) return "error!";
		
		//验证请求字符串里的时间戳
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date posTime = df.parse(context[4]);
		Date now = new Date();
		long between = (now.getTime() - posTime.getTime())/1000;
		if(!(between>0&&between<10)) return "error!";
		
		Pos pos = new Pos();
		pos.setUser__id(context[0]);
		pos.setMoney(Double.parseDouble(context[1]));
		pos.setUser__username(context[2]);
		pos.setDataTime(context[4]);
		posDao.save(pos);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return "ok";
	}
	
	
	
	/*
	public static void main(String[] args) throws Exception{
		WebServiceTest test=new WebServiceTest();
		InputStream in = new FileInputStream("C:/rsaKeyStore.keystore");
		KeyStore ks = KeyStore.getInstance("PKCS12", new BouncyCastleProvider());
		ks.load(in, "123456".toCharArray());
		Key pk = ks.getCertificate("mykey").getPublicKey();
		Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", new BouncyCastleProvider());
		cipher.init(Cipher.ENCRYPT_MODE, pk);
		byte[] deText=cipher.doFinal("hello world!".getBytes("UTF-8"));
		System.out.println(test.posRSA(new String(deText,"UTF-8")));
		
	}
	*/
	
}
