package administrator.dao;

import java.util.List;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import administrator.pojo.User;

import com.yyq.dao.MyHibernateDaoSupport;

@Service(value="userDao")
@RemotingDestination
public class UserDao extends MyHibernateDaoSupport<User>{
	/**
	 * @param username
	 * @param password
	 * @return 1(验证通过);-1(用户名或密码错误)
	 * 用户验证
	 */
	public int checkUser(String username, String password) {
		List<User> userList=getHibernateTemplate().find("from User where username=? and password=?", new Object[]{username,password});
		if(userList.size()==1){
			return userList.get(0).getUserType();
		}else{
			return -1;
		}
	}
	
	/**
	 * @param types用户类型
	 * @return
	 * 根据用户类型查找用户
	 */
	public List<User> findUserByTypes(Object[] types){
		StringBuffer sb=new StringBuffer("from User where userType=?");
		for(int i=types.length-1;i>0;i--){
			sb.append(" or userType=?");
		}
		log.info("types.length="+types.length);
		return getHibernateTemplate().find(sb.toString(),types);
	}

	/**
	 * 设置部门经理，取消原来的部门经理
	 * @param deptId
	 * @param id
	 */
	public void setDeptManager(String deptId, String userId) {
		jdbcTemplate.update("update dept set user_id=? where id=?", new Object[]{userId,deptId});
		jdbcTemplate.update("update User set userType=2 where dept__id=? and userType=1", new Object[]{deptId});
		jdbcTemplate.update("update User set userType=1 where dept__id=? and id=?", new Object[]{deptId,userId});
	}
	
	/**
	 * 检查用户名是否存在
	 * @param username
	 * @return
	 */
	public boolean isUsernameExist(String username){
		int length=getHibernateTemplate().find("from User where username=?", username).size();
		if(length==0){
			return false;
		}else{
			return true;
		}
	}
}