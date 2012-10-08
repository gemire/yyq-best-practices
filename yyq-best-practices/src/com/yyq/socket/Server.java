package com.yyq.socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;

import javax.swing.JFrame;

/**
 * Socket服务器端
 * @author YYQ
 */
public class Server extends JFrame{
	public Server() throws IOException{
		SnapShotTask snapShotTask=new SnapShotTask();
		//声明一个定时器来进行截图
		Timer timer=new Timer();
		//每隔250毫秒截一次图
		timer.schedule(snapShotTask, 0, 250);
		
		ServerSocket server = new ServerSocket(1234);
		while (true) {
			Socket socket = server.accept();
			byte[] buf=snapShotTask.buf.clone();
			//使用多线程来处理多个socket客户端的请求
			new SendThread(socket,buf,snapShotTask.x,snapShotTask.y).start();
		}
	}
	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.setSize(600, 400);
		server.setVisible(true);
		server.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	

}
