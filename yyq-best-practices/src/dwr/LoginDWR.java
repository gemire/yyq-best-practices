package dwr;

import org.directwebremoting.WebContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import administrator.dao.UserDao;

@Service
public class LoginDWR {
	@Autowired
	private UserDao userDao;
	
	/**
	 * 用AJAX进行用户登陆验证
	 */
	public String check(){
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		if("roleAnonymous".equals(username)){
			return "-1";
		}else{
			return username;
		}
		
//		HttpSession session = WebContextFactory.get().getSession();
//		if(session.getAttribute("username")==null||session.getAttribute("userType")==null){
//			return "ye,0";
//			//return "-1";
//		}else{
//			String username=(String)session.getAttribute("username");
//			String userType=(String)session.getAttribute("userType");
//			return username+","+userType;
//		}
	}
	
	/**
	 * 在新建用户时，利用AJAX检查用户名是否存在
	 * @param username
	 * @return
	 */
	public boolean checkUsername(String username){
		return userDao.isUsernameExist(username);
	}
	
	/**
	 * 用AJAX进行登出
	 */
	public void logout(){
		SecurityContextHolder.clearContext();
		//将session失效，这样也能使FlexSession重新刷新
		WebContextFactory.get().getSession().invalidate();
	}
}