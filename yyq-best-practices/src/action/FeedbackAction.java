package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.yyq.jms.JmsUtil;
import com.yyq.jstl.Page;
import com.yyq.struts2.MyAction;
import com.yyq.util.StringUtil;

import feedback.dao.FeedbackDao;
import feedback.pojo.Feedback;

@Results({
    @Result(name="ok",value="/ok.jsp"),
    @Result(name="listFeedback",value="/feedback/main.jsp"),
    @Result(name="seeFeedback",value="/feedback/seeFeedback.jsp")
})
public class FeedbackAction extends MyAction{
	@Autowired@Getter@Setter
	private FeedbackDao feedbackDao;
	@Autowired@Getter@Setter
	private JmsUtil jMSUtil;
	@Getter@Setter
	private Feedback feedback;
	@Getter@Setter
	private String keyWord="";
	@Getter@Setter
	private String id;
	
	public String newFeedback(){
		//PrintUtil.printlnAllProperties(feedback);
		feedback.setIsOK("否");
		feedback.setContent(StringUtil.clearEnter(feedback.getContent()));
		feedbackDao.save(feedback);
		put("gotoPage", "main.jsp");
		
		return "ok";
	}
	public String listFeedback(){
		if(keyWord!=null&&keyWord.length()!=0){
			list();//全文搜索
		}else{
			page=feedbackDao.paging(page);//普通分页
		}
		
		return "listFeedback";
	}
	public String findFeedback(){
		Feedback feedback=feedbackDao.findById(id);
		put("feedback", feedback);
		return "seeFeedback";
	}
	/**
	 * 发送邮件
	 * @return
	 */
	public String sendJms() {
		String msg=getParameter("msg");
		jMSUtil.sendMessage(msg);
		put("gotoPage", "main.jsp");
		return "ok";
	}
	/**
	 * 对留言标题和客户留言内容进行中文分词的全文搜索
	 */
	private void list(){
		List<Feedback> list=feedbackDao.findAll();
		List<Feedback> newList=new ArrayList<Feedback>();
		RAMDirectory directory = new RAMDirectory();
		Analyzer analyzer = new IKAnalyzer();
		
		try {
			IndexWriter  writer = new IndexWriter(directory, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
			for(Feedback feedback:list){
				Document doc = new Document();
				doc.add(new Field("title", feedback.getTitle(), Field.Store.YES, Field.Index.ANALYZED));
				doc.add(new Field("content", feedback.getContent(), Field.Store.YES, Field.Index.ANALYZED));
				writer.addDocument(doc);
			}
			writer.close();
			IndexSearcher searcher = new IndexSearcher(directory);
			/*下面这个表示要同时搜索这两个域,而且只要一个域里有满足我们搜索的内容就行了*/  
			BooleanClause.Occur[] clauses = {BooleanClause.Occur.SHOULD,BooleanClause.Occur.SHOULD};
			//System.out.println(keyWord);
			Query query = MultiFieldQueryParser.parse(Version.LUCENE_30, keyWord, new String[]{"title","content"}, clauses, analyzer);
			TopDocs rs = searcher.search(query, null, 1000);
			//System.out.println(rs.totalHits);
			for(ScoreDoc docc:rs.scoreDocs){
				newList.add(list.get(docc.doc));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		page=new Page<Feedback>();
		page.setList(newList);
	}
}
