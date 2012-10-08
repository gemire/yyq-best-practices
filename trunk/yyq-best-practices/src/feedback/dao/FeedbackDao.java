package feedback.dao;

import org.springframework.stereotype.Service;

import feedback.pojo.Feedback;

import com.yyq.dao.MyHibernateDaoSupport;
import com.yyq.jstl.Page;

@Service(value="feedbackDao")
public class FeedbackDao extends MyHibernateDaoSupport<Feedback>{
	/**
	 * 根据关键字搜索，现在已经用Lucene替代
	 * @param keyWord
	 * @param page
	 * @return
	 */
	public Page<Feedback> listFeedback(String keyWord,Page<Feedback> page){
		String sql="from Feedback where LOCATE(?,title)<>0";
		page=paging(sql,page,keyWord);
		
		return page;
	}
	/**
	 * 统计用户留言总数，现在已经未使用
	 * @param keyWord
	 * @return
	 */
	public int countFeedback(String keyWord){
		return jdbcTemplate.queryForInt("SELECT count(id) from feedback where LOCATE(?,title)<>0",new Object[]{keyWord});
	}
	/**
	 * 未回复留言列表
	 * @param page
	 * @return
	 */
	public Page<Feedback> noAnswerList(Page<Feedback> page) {
		Feedback f=new Feedback();
		f.setIsOK("否");
		return paging(f, page);
	}
	/**
	 * 回复客户留言
	 * (原来在SaleAction的answerFeedback()中使用，现已弃用，由泛型Dao中的updateAuto方法取代)
	 * @param feedback
	 */
	public void ananswerFeedback(Feedback feedback) {
		Feedback f=findById(feedback.getId());
		f.setAnswer(feedback.getAnswer());
		f.setUser__id(feedback.getUser__id());
		f.setIsOK("是");
		saveOrUpdate(f);
	}
}