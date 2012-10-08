package authority.dao;

import java.util.List;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import authority.pojo.Authorityset;

import com.yyq.dao.MyHibernateDaoSupport;

@Service(value="authoritysetDao")
@RemotingDestination
public class AuthoritysetDao extends MyHibernateDaoSupport<Authorityset>{
	/**
	 * 查询用户所具有的权限
	 * @param uid
	 * @return
	 */
	public List<String> findAllAuthorities(String uid) {
		return jdbcTemplate.queryForList("select authority__id from authorityset where user__id=?", new Object[]{uid},String.class);
	}

	/**
	 * 删除用户的权限
	 * @param uid
	 */
	public void deleteByUid(String uid) {
		jdbcTemplate.update("delete from authorityset where user__id=?",new Object[]{uid});
	}

	/**
	 * 更新用户的权限
	 * @param uid
	 * @param authorities
	 */
	public void updateAuthority(String uid, String[] authorities) {
		Authorityset authority=new Authorityset();
		authority.setUser__id(uid);
		if(authorities==null) return;
		for(String s:authorities){
			authority.setAuthority__id(s);
			getHibernateTemplate().save(authority);
		}
	}
}