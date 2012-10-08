package action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import lombok.Getter;
import lombok.Setter;
import mp3.dao.Mp3Dao;
import mp3.pojo.Mp3;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import sale.dao.InsuDao;

import administrator.vo.New;
import authority.dao.AuthorityDao;
import authority.dao.AuthoritysetDao;
import administrator.pojo.User;
import administrator.dao.UserDao;
import authority.pojo.Authority;

import dept.dao.DeptDao;
import dept.pojo.Dept;

import com.mortennobel.imagescaling.ResampleOp;
import com.yyq.struts2.MyAction;
import com.yyq.util.HttpUtil;

@Results({
	@Result(name="gotoUserList",value="WEB-INF/web/administrator/userList.jsp"),
	@Result(name="gotoUser",value="WEB-INF/web/administrator/user.jsp"),
	@Result(name="jms",value="welcome.jsp"),
	@Result(name="listPic",value="pic/listPic.jsp"),
	@Result(name="listNews",value="WEB-INF/web/administrator/listNews.jsp"),
	@Result(name="gotoMp3Play",value="WEB-INF/web/administrator/mp3Play.jsp"),
	@Result(name="addMp3",value="WEB-INF/web/administrator/addMp3.jsp"),
	@Result(name="gotoAuthority",value="WEB-INF/web/administrator/authority.jsp"),
	@Result(name="saleStatistics",value="WEB-INF/web/administrator/saleStatistics.jsp")
})
public class UserManageAction extends MyAction{
	@Autowired@Getter@Setter
	private UserDao userDao;
	@Autowired@Getter@Setter
	private DeptDao deptDao;
	@Autowired@Getter@Setter
	private AuthorityDao authorityDao;
	@Autowired@Getter@Setter
	private AuthoritysetDao authoritySetDao;
	@Autowired@Getter@Setter
	private InsuDao insuDao;
	@Autowired@Getter@Setter
	private Mp3Dao mp3Dao;
	@Getter@Setter
	private String uid=null;
	@Getter@Setter
	private User user;
	@Getter@Setter
	private File file;
	
	/**
	 * 查看用户
	 */
	public String gotoUser(){
//    	System.out.println((String)ac.getParameters().get("uid"));
//		int uid=Integer.parseInt((String)ac.getParameters().get("uid"));
		//System.out.println(uid);
		User user=userDao.findById(uid);
		put("user", user);
		List<Dept> deptList=deptDao.findAll();
		put("deptList", deptList);
		return "gotoUser";
	}
	/**
	 * 新建用户或者更新用户
	 * @return
	 */
	public String saveOrUpdateUser(){
		//user.setName(StringUtil.ISO_UTF8(user.getName()));
		//PrintUtil.printlnAllProperties(user);
		
		if(user.getPassword().length()!=32) user.setPassword(new Md5PasswordEncoder().encodePassword(user.getPassword(),null));
		userDao.saveOrUpdate(user);
		//设置部门经理
		if(user.getUserType()==1){
			userDao.setDeptManager(user.getDept__id(),user.getId());
		}
		//PrintUtil.printlnAllProperties(user);
		return returnToUserList();
	}
	/**
	 * 删除用户及其权限
	 * @return
	 */
	public String deleteUser(){
//		authoritySetDao.deleteByUid(uid);
		userDao.deleteById(uid);
		return returnToUserList();
	}
	/**
	 * 新建用户
	 */
	public String newUser(){
//		Map<String,Object> map=ac.getContextMap();
		List<Dept> deptList=deptDao.findAll();
		put("deptList", deptList);
		return "gotoUser";
	}
	/**
	 * 查找pic文件夹下的图片，并生成图片的缩略图
	 */
	public String listPic(){
		List<String> list=new ArrayList<String>();
		String s;
		double width_t=192.0;//缩略图的宽度
		double height_t=120.0;//缩略图的高度
		
		String filePath=ServletActionContext.getServletContext().getRealPath("/")+"pic";
		File file = new File(filePath);
		File destFile;
		File[] array = file.listFiles();
		for(File f:array){
			s=f.getName();
			if(s.endsWith(".jpg")){
				list.add(s);
			
				//生成图片缩略图
				try{
					destFile=new File(filePath+"\\smallPic\\"+f.getName());
					if(!destFile.exists()){
						BufferedImage yuan=ImageIO.read(f);
						//将图片等比例缩小
						int width=yuan.getWidth();
						int height=yuan.getHeight();
						if(width/height>1.6){
							height=(int)(width_t/width*height);
							width=(int)width_t;
						}else{
							width=(int)(height_t/height*width);
							height=(int)height_t;
						}
						ResampleOp resampleOp=new ResampleOp(width,height);
						BufferedImage rescaledTomato=resampleOp.filter(yuan, null);
						ImageIO.write(rescaledTomato, "JPEG", destFile);
					}
				}catch(Exception e){
					log.error("图片缩小时出现错误！");
					e.printStackTrace();
				}
			}
		}
		log.info("共有图片"+list.size()+"张。");
		put("list", list);
		return "listPic";
	}
	/**
	 * 图片上传
	 */
	public String picUpload(){
		if(file!=null){
			String filePath=ServletActionContext.getServletContext().getRealPath("/")+"pic";
			File destFile = new File(filePath);
			try {
				FileUtils.copyFileToDirectory(file,destFile);
				String fileName=filePath+"\\"+file.getName();
				destFile=new File(fileName);
				destFile.renameTo(new File(fileName.replace(".tmp", ".jpg")));
			} catch (IOException e) {
				log.error("文件上传写入磁盘错误！");
				e.printStackTrace();
			}
		}
		
		return listPic();
	}
	/**
	 * 图片删除
	 */
	public String picDelete(){
		String fileName=getParameter("fileName");
		String file=ServletActionContext.getServletContext().getRealPath("/")+"pic/"+fileName;
		new File(file).delete();
		return listPic();
	}
	/**
	 * 所有员工月份销售情况统计
	 */
	public String saleStatistics(){
		List<Map> saleStatisticsList=insuDao.saleStatistics();
		//System.out.println(saleStatisticsList.size());
		DefaultCategoryDataset cds = new DefaultCategoryDataset();
		for(Map map:saleStatisticsList){
			cds.addValue(Integer.parseInt(String.valueOf(map.get("num"))), (String)map.get("username"), (String)map.get("month"));
		}
		defaultLineChart("所有员工月份销售情况统计图", "月份", "销售量", cds);
		return "saleStatistics";
	}
	/**
	 * 查看新闻（利用来获取目标页面，然后将页面中需要的内容截取出来）
	 * (2010.6.10修复)
	 */
	public String listNews(){
		String res=HttpUtil.getHTMLStr("http://www.javaeye.com/news");
		
		//System.out.println(res);
		int start;
		int end=0;
		int time=0;
		New news;
		List<New> newsList=new ArrayList<New>();
		res=res.substring(res.indexOf("index_main"));
		while(res.indexOf(" <a href='/news/",end)!=-1){
			news=new New();
			start=res.indexOf(" <a href='/news/",end)+10;
			end=res.indexOf("'", start);
			news.setHref("http://www.javaeye.com"+res.substring(start, end));
			
			start=res.indexOf("title='", end)+7;
			end=res.indexOf("'>", start);
			news.setTitle(res.substring(start, end));
			
			start=res.indexOf("</a></h3></div>", end)+15;
			end=res.indexOf("</div>", start);
			news.setInfo(res.substring(start, end));
			
			start=res.indexOf("date'>", end)+6;
			end=res.indexOf("</span>", start);
			news.setDate(res.substring(start, end));
			
			newsList.add(news);
			
			time++;
			if(time>24){
				log.info("限制新闻数量："+time);
				break;
			}
		}
		
		put("newsList", newsList);
		
		return "listNews";
	}
	/**
	 * 查找mp3文件
	 * @return
	 */
	public String listMp3(){
		List<String> dirList=mp3Dao.findPropertyValues("dir");
		List<List<Mp3>> mp3List=new ArrayList<List<Mp3>>();
		for(String dir:dirList){
			mp3List.add(mp3Dao.findMp3(dir));
		}
		put("dirList",dirList);
		put("mp3List",mp3List);
		return "gotoMp3Play";
	}
	/**
	 * 删除歌曲
	 * @return
	 */
	public String deleteMp3(){
		String id=getParameter("id");
		Mp3 mp3=mp3Dao.findById(id);
		File file=new File("D:/mp3/temp/"+mp3.getFileName());
		file.delete();
		mp3Dao.delete(mp3);
		return listMp3();
	}
	/**
	 * 可以添加的Mp3列表
	 * @return
	 */
	public String addList(){
		List<String> mp3list=mp3Dao.findPropertyValues("srcFile");
		
		String dir="D:/mp3";
		File mp3Dir = new File(dir);
		File[] directorys = mp3Dir.listFiles();
		File[] files;
		List<String> dirList=new ArrayList<String>();
		List<List<String>> mp3List=new ArrayList<List<String>>();
		List<String> list;
		for(File f:directorys){
			if(f.isDirectory()){
				if("temp".equals(f.getName())) continue;
				dirList.add(f.getName());
				list=new ArrayList<String>();
				files=f.listFiles();
				for(File f2:files){
					if(!mp3list.contains(f2.getAbsolutePath())){
						list.add(f2.getName());
					}
				}
				mp3List.add(list);
			}
		}
		put("path",dir);
		put("dirList",dirList);
		put("mp3List",mp3List);
		return "addMp3";
	}
	/**
	 * 添加Mp3
	 * @return
	 */
	public String addMp3(){
		String displayName=getParameter("displayName");
		String dir=getParameter("dir");
		String srcFile="D:\\mp3\\"+dir+"\\"+displayName;
		String fileName=UUID.randomUUID().toString()+".mp3";
		try {
			FileUtils.copyFile(new File(srcFile), new File("D:/mp3/temp/"+fileName));
		} catch (IOException e) {
			e.printStackTrace();
			return listMp3();
		}
		Mp3 mp3=new Mp3();
		mp3.setSrcFile(srcFile);
		mp3.setDir(dir);
		mp3.setFileName(fileName);
		mp3.setDisplayName(displayName.replaceAll("[.]mp3", ""));
		mp3Dao.save(mp3);
		
		return listMp3();
	}
	/**
	 * 跳转到用户列表
	 */
	public String returnToUserList(){
		listUser();
		return "gotoUserList";
	}
	/**
	 * 跳转到计算器(SWF)
	 */
	public String goToCalculator(){
		return gotoSWF("calculator");
	}
	/**
	 * 跳转到3D连连看(SWF)
	 */
	public String goTo3DLink(){
		return gotoSWF("Link3D");
	}
	/**
	 * 跳转到部门管理(SWF)
	 */
	public String goToDeptManage(){
		return gotoSWF("deptManage");
	}
	/**
	 * 跳转到3D模型Web展示页面(SWF)
	 */
	public String goToDAETest(){
		return gotoSWF("DAETest");
	}
	/**
	 * 查找用户权限
	 */
	public String gotoAuthority(){
		List<String> authorities=authoritySetDao.findAllAuthorities(uid);
		put("authorities", authorities);
		
		List<Authority> authoritylist=authorityDao.findAll();
		put("authoritylist", authoritylist);
		
		return "gotoAuthority";
	}
	/**
	 * 更新用户权限
	 */
	public String updateAuthority(){
		String[] authorities=getParameterValues("authority");
		
		authoritySetDao.deleteByUid(uid);
		authoritySetDao.updateAuthority(uid,authorities);
		
		showMessage("用户权限更新成功！");
		
		return returnToUserList();
	}
	/**
	 * 将查询列表独立出来
	 */
	private void listUser(){
		List<User> userList=userDao.findUserByTypes(new Object[]{0,1,2});
		log.info("系统用户数="+userList.size());
		put("userList", userList);
	}
}
