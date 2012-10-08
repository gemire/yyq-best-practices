package com.yyq.socket;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TimerTask;

import javax.imageio.ImageIO;

/**
 * 服务器端的截图类
 * @author YYQ
 */
public class SnapShotTask extends TimerTask{
	public BufferedImage screenshot;
	public byte[] buf;
	public int x;
	public int y;
	private Robot robot=null;
	private ByteArrayOutputStream os;
	
	private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	private Rectangle rec=new Rectangle(0, 0, (int)size.getWidth(), (int)size.getHeight());//截图范围：全屏
	public SnapShotTask(){
		try {
			robot=new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
//		System.out.println("抓图");
		screenshot = robot.createScreenCapture(rec);
		Point mousePoint=MouseInfo.getPointerInfo().getLocation();
		x=mousePoint.x;
		y=mousePoint.y;
//		try {
//			test();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
		
		try {
			os = new ByteArrayOutputStream();
			//模拟写到输出流，再转化为byte[]，减少IO操作
			try{
				ImageIO.write(screenshot, "jpeg",os);
			}catch(Exception e){
				System.out.println("出错了！");
			}
			buf=os.toByteArray();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void test() throws IOException{
		BufferedImage image=screenshot;
  	File file=new File("tempY.jpg");
    FileOutputStream fileout=new FileOutputStream(file);
    ImageIO.write(image,"jpeg",fileout);
    fileout.close();
	}
}
