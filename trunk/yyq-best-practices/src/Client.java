import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * Socket客户端，
 * 向Socket服务器端发送请求，
 * 并将接收到的图片显示给客户
 * @author YYQ
 */
public class Client extends JFrame implements Runnable{
	private String ip="192.168.53.9";//服务器端ip地址
	public void run(){
		try{
			Socket socket = new Socket(ip, 1234);
			DataInputStream in=new DataInputStream(socket.getInputStream());
			
			int x=in.readInt();
			int y=in.readInt();
			
			byte[] buf =new byte[65536];
			
			File file=new File("temp.jpg");
	    FileOutputStream fileout=new FileOutputStream(file);
	    int length;
	    while((length=in.read(buf))!=-1){
	    	fileout.write(buf,0,length);
	    }
	    
	    fileout.close();
			
//			System.out.println(buf.length);
	    //载入截图
	    BufferedImage bigPic=ImageIO.read(new File("temp.jpg"));
	    int width=bigPic.getWidth();
	    int height=bigPic.getHeight();
	    //将图片显示在窗体上
			getGraphics().drawImage(bigPic, 0, 0, null);
			//载入鼠标光标
			BufferedImage img=ImageIO.read(Client.class.getResource("ok.png"));
			//将PNG中透明的点设为背景图的颜色
			for(int i=0;i<img.getWidth();i++){
				for(int j=0;j<img.getHeight();j++){
					if(img.getRGB(i, j)==-1){//避免光标跑出屏幕范围报错
						if(x+i<width&&y+j<height) img.setRGB(i, j, bigPic.getRGB(x+i, y+j));
					}
				}
			}
			//画出鼠标光标
			getGraphics().drawImage(img, x, y,null);
			
			Thread.sleep(250);//每250毫秒向服务器端发出一次请求
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		Client client = new Client();
		client.setSize(600, 400);
		client.setVisible(true);
		client.setDefaultCloseOperation(EXIT_ON_CLOSE);
		if(args.length==1){
			client.ip=args[0];
		}
		
		Thread thread=new Thread(client);
		while(true){
			thread.run();
		}
	}
}
