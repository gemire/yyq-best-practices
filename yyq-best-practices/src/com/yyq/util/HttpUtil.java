package com.yyq.util;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

/**
 * HTTP工具类
 * @author YYQ
 */
public class HttpUtil {
	private final static Logger log = Logger.getLogger(HttpUtil.class);
	public static String getHTMLStr(String url){
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String res=null;
		try {
			res = httpclient.execute(httpget, responseHandler);
		} catch (Exception e) {
			log.error("Http读取错误！");
			e.printStackTrace();
		} finally{
			httpclient.getConnectionManager().shutdown();
		}
		return res;
	}
}
