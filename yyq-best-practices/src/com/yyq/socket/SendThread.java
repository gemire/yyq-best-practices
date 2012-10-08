package com.yyq.socket;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 服务器端发送类，
 * 采用多线程实现
 * @author YYQ
 */
public class SendThread extends Thread{
	private DataOutputStream dos;
	private byte[] buf;
	private int x;
	private int y;
	public SendThread(Socket s,byte[] buf,int x,int y) throws IOException{
		dos=new DataOutputStream(s.getOutputStream());
		this.buf=buf;
		this.x=x;
		this.y=y;
	}
	public void run(){
		try {
			dos.writeInt(x);
			dos.writeInt(y);
			
			int start=0;
			int end=buf.length;
			while(start<=end){
				if(start+65536>end){
					try{
					dos.write(buf,start,end-start);
					}catch(Exception e){
						e.printStackTrace();
					}
				}else{
					dos.write(buf, start, 65536);
				}
				start+=65536;
			}
			dos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
