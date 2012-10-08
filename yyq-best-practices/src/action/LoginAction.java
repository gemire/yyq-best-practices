package action;

import lombok.Getter;
import lombok.Setter;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.yyq.struts2.MyAction;

@Results({
    @Result(name="success",value="main.jsp"),
    @Result(name="faile",value="login.jsp")
})
public class LoginAction extends MyAction{
	@Getter@Setter
	private String username;
	@Getter@Setter
	private String password;
	@Getter@Setter
	private String captchafield;

	/**
	 * 将登陆成功后的业务查询动作移到各个模块中，以提高可维护性
	 */
	public String login() {
		
//		Map<String,Object> session=ActionContext.getContext().getSession();
//		Captcha captcha=(Captcha)session.get("simpleCaptcha");
//		if(!captcha.isCorrect(captchafield)) return "faile";
//		PersonDao personDAO=(PersonDao)SpringUtil.getBean("personDao");
//		int userType=personDAO.checkUser(username,password);
//		log.info("userType="+userType);
//		if(userType==-1){
//			return "faile";
//		}
//		System.out.println(username);
//		session.put("userType", String.valueOf(userType));
//		session.put("username", username);
		
		return "success";
	}
}
