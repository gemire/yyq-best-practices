package dwr;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.springframework.stereotype.Service;

/**
 * AJAX聊天室工具类
 * @author YYQ
 */
@Service
public class MessageUtil {
	private List<String> userList=new LinkedList<String>();
	public Util getUtilAll(){
		WebContext wctx=WebContextFactory.get();
		String currentPage=wctx.getCurrentPage();
		Collection sessions=wctx.getScriptSessionsByPage(currentPage);
		return new Util(sessions);
	}
	/**
	 * 向所有用户发送消息
	 * @param username
	 * @param message
	 */
	public void sendMessage(String username,String message){
		getUtilAll().addFunctionCall("newMessage",username+"说："+message);
	}
	/**
	 * 用户加入聊天室后调用的方法
	 * @param username
	 */
	public void addRoom(String username){
		getUtilAll().addFunctionCall("newMessage",username+"加入了聊天室！");
		boolean isContain=false;
		for(String userName:userList){
			if(userName.equals(username)) isContain=true;
		}
		if(isContain==false) userList.add(username);
		getUtilAll().addFunctionCall("reflash",userList);
	}
	/**用户离开聊天室后调用的方法
	 * @param username
	 */
	public void leftRoom(String username){
		getUtilAll().addFunctionCall("newMessage",username+"退出了聊天室！");
		for(int i=0;i<userList.size();i++){
			if(username.equals(userList.get(i))){
				userList.remove(i);
				break;
			}
		}
		getUtilAll().addFunctionCall("reflash",userList);
	}
}
