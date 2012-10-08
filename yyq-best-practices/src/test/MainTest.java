package test;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MainTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date date1 = df.parse("2009-05-27 09:57:58");
//		Date date2 = df.parse("2009-05-27 09:58:02");
//		System.out.println(date2.getTime());
//		System.out.println(date1.getTime());
//		long between = (date2.getTime() - date1.getTime())/1000;
//		System.out.println(between);
//		System.out.println(UUID.randomUUID().toString().length());
//		173324048452438504237862034227457928988787042269100320358027101260512634166530635513956289478644409996345947983052751241098244143794611501546349142495419280298633187049165962303719370053240523980738454041899975319529498410370702268058401750940393755274043545655021318381837189898253433952962439443747623297677;
		
		//调整mp3文件名
		String filePath="D:/mp3";
		File file = new File(filePath);
		String s;
		int i,j;
		File[] array = file.listFiles();
		for(File f:array){
			s=f.getName();
			i=s.indexOf("-");
			if(i!=-1){
//				j=s.indexOf(".");
				s=s.substring(0, i)+" - "+s.substring(i+1);
				f.renameTo(new File(filePath+"/"+s));
			}
		}
		
//		//查找哪些mp3还没有放到U盘里
//		String path1="D:\\mp3";
//		String path2="F:\\mp3";
//		File file = new File(path1);
//		File file3;
//		File[] directorys = file.listFiles();
//		File[] files;
//		for(File f:directorys){
//			if(f.isDirectory()){
//				files=f.listFiles();
//				for(File file2:files){
//					file3=new File(file2.getPath().replace(path1, path2));
//					if(!file3.exists()) System.out.println(file2.getPath());
//				}
//			}
//		}
		
//		System.out.println(Long.toHexString(System.currentTimeMillis())+Long.toHexString(System.nanoTime()));
		
//		String[] str="1,,".split(",");
//		System.out.println(str.length);3b64878f6bf44d259ac37d67526bb0da
//		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
//		User user=new User();
//		user.id="";
//		ifEmptyToNull(user.id);
//		System.out.println(user.id.length());
	}
	public static void ifEmptyToNull(String id){
		if(id!=null&&id.length()==0){
			id=null;
		}
	}
	
}
class User{
	public String id;
}
