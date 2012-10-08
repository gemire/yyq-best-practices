package h2db;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.h2.tools.Server;

public class H2TcpServer{
	private Server server;
	public H2TcpServer(){
		String path=this.getClass().getResource("").toString();
		String url;
		
		StringBuffer sb=new StringBuffer();
		sb.append("jdbc:h2:tcp://localhost/D:/");
//		sb.append(path);
		sb.append("yyq"); // 数据库名
		url=sb.toString();
		sb.append(";USER=");
		sb.append("yyq"); // 用户名
		sb.append(";PASSWORD=");
		sb.append("yyq"); // 密码
		sb.append(";IFEXISTS=TRUE");
		
		String h2arg=sb.toString();
		System.out.println("H2数据库的启动参数："+h2arg);
		try {
			server=Server.createTcpServer(h2arg).start();
		} catch (SQLException e) {
			System.out.println("H2数据库启动失败！");
			e.printStackTrace();
		}
		
//		String filePath=path.replaceAll("file:/", "").replaceAll("%20", " ").replaceAll("h2db/", "")+"application.properties";
//		Properties properties = new Properties();
//		try {
//			properties.load(new FileInputStream(filePath));
//			properties.setProperty("jdbc.url", url);
//			properties.store(new FileOutputStream(filePath), "jdbc.url");
//		} catch (IOException e) {
//			System.out.println("载入application.properties配置文件出错！");
//			e.printStackTrace();
//		}
//		System.out.println("ok.");
	}
	protected void finalize(){
		server.stop();
	}
	public static void main(String[] args) {
		new H2TcpServer();
	}
}
