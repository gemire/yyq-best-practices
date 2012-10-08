package com.yyq.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.yyq.context.RequestHelper;

import administrator.pojo.User;
import administrator.dao.UserDao;

/**
 * 自定义用户认证类
 * @author YYQ
 */
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RequestHelper requestHelper;
	//根据用户名查找用户权限的sql语句
	private String queryAuthoritiesByUsername="select a.authority as authority from (select id,username from user where username=?) u join authorityset al on u.id = al.user__id join authority a on a.id = al.authority__id";
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		User user=userDao.findUniqueBy("username", username);
		if (user == null) {
			throw new UsernameNotFoundException("用户" + username + " 不存在");
		}
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		List<String> authorityStrs=jdbcTemplate.queryForList(queryAuthoritiesByUsername,String.class,username);
		for(String authorityStr:authorityStrs){
			authSet.add(new GrantedAuthorityImpl(authorityStr));
		}
		//动态权限管理，所有已登录的用户都具有这个权限
		authSet.add(new GrantedAuthorityImpl("ROLE_LOGIN"));
		
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		boolean enabled="true".equals(user.getEnabled())?true:false;
		
		String timestamp=requestHelper.getNowTime();
		String password=user.getPassword();
		//密码的加密方式为：SHA-1(MD5(密码)+时间戳)
		password=new ShaPasswordEncoder().encodePassword(password+timestamp,null);
		
		MyUserDetails myUserDetails=new MyUserDetails(user.getUsername(),password,enabled,accountNonExpired,credentialsNonExpired,accountNonLocked,authSet);
		myUserDetails.setId(user.getId());
		
		//将用户地址查询及天气预报移到CommonAction，以提高用户登录的速度。
		
		return myUserDetails;
	}
}