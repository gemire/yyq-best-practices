package sale.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import sale.pojo.Carhost;

import com.yyq.dao.MyHibernateDaoSupport;

@Service(value="carhostDao")
public class CarhostDao extends MyHibernateDaoSupport<Carhost>{
	
	/**
	 * 查询某销售员处理过的所有用户的信息
	 * @param inputOperator
	 * @return
	 */
	public List<Carhost> findCarhost(String inputOperator){
		//joinDao.findAll(Insu_Carhost.class);1
		return getHibernateTemplate().find("from Carhost where id in (SELECT distinct carhost__id from Insu where user__username=?)", inputOperator);
	}
}