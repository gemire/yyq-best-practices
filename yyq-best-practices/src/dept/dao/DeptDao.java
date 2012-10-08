package dept.dao;

import java.util.List;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import dept.pojo.Dept;
import dept.vo.DeptReport;

import com.yyq.dao.MyHibernateDaoSupport;

@Service(value="deptDao")
@RemotingDestination
public class DeptDao extends MyHibernateDaoSupport<Dept>{
	/**
	 * 部门销售情况
	 * @param username
	 * @return
	 */
	public List<DeptReport> findDeptReport(String username){
		return jdbcTemplate.query("select u.username,u.name,count(i.id) sumInsu,ifnull(sum(i.sumFee),0) sumFee from (select username,name from User where dept__id=(select dept__id from User where username=?) and userType=2) u left outer join Insu i on u.username=i.user__username group by u.username",new Object[]{username},new BeanPropertyRowMapper(DeptReport.class));
	}
	
	/**
	 * 查询某部门下的所有员工的id和姓名，格式“id@name”
	 * @param deptName
	 * @return
	 */
	public List<String> namesInDept(String deptName){
		return jdbcTemplate.queryForList("select CONCAT(id,'@',name) from User where dept__id=(select id from Dept where name=?)",new Object[]{deptName},String.class);
	}
	
	/**
	 * 删除部门，但不能删除非空的部门
	 * @param deptName
	 * @return
	 */
	public String deleteDept(String deptName){
		try {
			int numPerson=jdbcTemplate.queryForInt("select count(id) from user where dept__id=(select id from dept where name=?)", new Object[]{deptName});
			if(numPerson!=0) return "err"; // 如果部门非空，则返回“err”字符串
			jdbcTemplate.execute("delete from dept where name='"+deptName+"'");
			log.info("删除部门："+deptName);
			return "ok";
		} catch (RuntimeException e) {
			log.error("DeptDao.deleteDept()出错！", e);
			throw e;
		}
	}
	
	/**
	 * 建立新的部门
	 * @param deptName
	 * @return
	 */
	public String newDept(String deptName){
		try {
			int isNone=jdbcTemplate.queryForInt("select count(id) from dept where name=?",new Object[]{deptName});
			if(isNone!=0) return "err"; // 如果部门已经存在，则返回“err”字符串
			Dept dept=new Dept();
			dept.setName(deptName);
			getHibernateTemplate().save(dept);
			log.info("新建部门："+deptName);
			return "ok";
		} catch (RuntimeException e) {
			log.error("DeptDao.newDept()出错！", e);
			throw e;
		}
	}

	/**
	 * 通过用户名查询部门名称
	 * @param username
	 * @return
	 */
	public String getDeptNamebyUsername(String username) {
		return (String)jdbcTemplate.queryForObject("select name from dept where id=(select dept__id from user where username=?)", new Object[]{username}, String.class);
	}
}