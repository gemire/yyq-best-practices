package authority.dao;

import org.springframework.stereotype.Service;

import authority.pojo.Authority;

/**
 * @author YYQ ORMTool
 */

import com.yyq.dao.MyHibernateDaoSupport;

@Service(value="authorityDao")
public class AuthorityDao extends MyHibernateDaoSupport<Authority>{

}