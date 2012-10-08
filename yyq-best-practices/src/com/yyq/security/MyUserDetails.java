package com.yyq.security;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 自定义用户认证信息
 * @author YYQ
 */
public class MyUserDetails extends User{
	private static final long serialVersionUID = 1L;
	@Getter@Setter private String id;//添加用户ID
	public MyUserDetails(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Collection<GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	}
}