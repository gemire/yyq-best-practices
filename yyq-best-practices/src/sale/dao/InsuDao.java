package sale.dao;

import java.util.List;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import sale.pojo.Insu;

import com.yyq.dao.MyHibernateDaoSupport;
import com.yyq.util.DateUtil;

@Service(value="insuDao")
@RemotingDestination
public class InsuDao extends MyHibernateDaoSupport<Insu>{
	/**
	 * 查询某操作员销售过的所有保单
	 * @param inputOperator
	 * @param page
	 * @return
	 */
//	public Page findAllInsu(Insu insu,Carhost host,Page page){
//		page=pagingJoin(page,new Condition(insu,"carhost__id",host,"id").locate("Insu.inputDateTime", insu.getInputDateTime()));
//		return page;
//	}
	
	/**
	 * 所有员工月份销售情况
	 * 演示使用jdbcTemplate查询，将结果封装为Map
	 * @return
	 */
	public List saleStatistics(){
		return jdbcTemplate.queryForList("select user__username as username,left(inputDateTime,7) as month,sum(1) as num,sum(sumFee) as sumFee from insu group by user__username,left(inputDateTime,7) order by month");
	}
	
	/**
	 * 统计员工总的保单销售量，在sumInsu.mxml中使用
	 * @param inputOperator
	 * @return
	 */
	public int countInsu(String inputOperator){
		return jdbcTemplate.queryForInt("SELECT count(id) from insu where user__username=?",new Object[]{inputOperator});
	}
	
	/**
	 * 统计今天总的保单销售量
	 * @return
	 */
	public int countInsuToday(){
		return jdbcTemplate.queryForInt("SELECT count(id) from insu where inputDateTime LIKE concat(?,'%')",new Object[]{DateUtil.getDate()});
	}
}