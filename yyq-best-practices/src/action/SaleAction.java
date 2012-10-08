package action;

import java.io.File;

import lombok.Getter;
import lombok.Setter;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import sale.dao.SaleMDao;
import sale.pojo.Carhost;
import sale.pojo.CarhostHelper;
import sale.pojo.Insu;
import sale.to.Insu_Carhost;

import com.yyq.orm.Like;
import com.yyq.struts2.MyAction;
import com.yyq.util.DateUtil;
import com.yyq.util.ExcelUtil;
import com.yyq.util.SSUtil;

import feedback.dao.FeedbackDao;
import feedback.pojo.Feedback;

@Results({
	@Result(name="success",value="WEB-INF/web/employee/insuList.jsp"),
	@Result(name="gotoSale",value="WEB-INF/web/employee/sale.jsp"),
	@Result(name="gotoInsuImprot",value="WEB-INF/web/employee/insuImport.jsp"),
	@Result(name="noAnswerList",value="WEB-INF/web/employee/noAnswerList.jsp")
})
public class SaleAction extends MyAction{
	
	@Autowired@Getter@Setter
	private SaleMDao saleMDao;
	
//	@Autowired@Getter@Setter
//	private CarhostDao carhostDao;
//	@Autowired@Getter@Setter
//	private InsuDao insuDao;
	@Autowired@Getter@Setter
	private FeedbackDao feedbackDao;
	@Getter@Setter
	private Insu insu;
	@Getter@Setter
	private Carhost host;
	@Getter@Setter
	private File file;
	@Getter@Setter
	private Feedback feedback;
	
	/**
	 * 销售过程
	 */
	@Transactional
	public String sale() {
		Carhost host_old=null;
		if(host.getId()!=null&&host.getId().length()!=0){
			host_old=saleMDao.getCarhostDao().findById(host.getId());
		}
		
		if(host_old==null||!CarhostHelper.isEquals(host,host_old)){
			host.setId(null);
			saleMDao.getCarhostDao().saveOrUpdate(host);
		}
		
		insu.setCarhost__id(host.getId());
		String username=SSUtil.getName();
		insu.setUser__username(username);
		insu.setInputDateTime(DateUtil.nowTime());
		saleMDao.getInsuDao().saveOrUpdate(insu);
		
		gotoMessagePage("销售成功！","sale!returnToSaleList.action");
		
		return null;
		
//		listAllInsu();
		
//		return "success";
	}
	/**
	 * 保单导入(Excel导入)
	 * @return
	 */
	public String insuImport(){
		HSSFWorkbook workbook=ExcelUtil.loadExcel(file);
		String now=DateUtil.nowTime();
		HSSFSheet childSheet = workbook.getSheetAt(0);
		HSSFRow row;
		Carhost host;
		Carhost host_old=null;
		int sum=0;
		for(int r=1; r < childSheet.getPhysicalNumberOfRows(); r++) {//循环该 子sheet row
			row=childSheet.getRow(r);
			host=new Carhost();
			host.setCarHostName(ExcelUtil.getString(row, 0));//客户姓名
			host.setCarHostCode(ExcelUtil.getString(row, 1));//身份证号码
			host.setSex(ExcelUtil.getString(row, 2));//性别
			host.setAge(Integer.parseInt(ExcelUtil.getString(row, 3)));//年龄
			host.setAddress(ExcelUtil.getString(row, 4));//住址
			host.setLinkTel(ExcelUtil.getString(row, 5));//联系电话
			host_old=saleMDao.getCarhostDao().findUniqueByExample(host);
			if(host_old==null){
				saleMDao.getCarhostDao().save(host);
			}else{
				host.setId(host_old.getId());
			}
			
			insu=new Insu();
			insu.setCarhost__id(host.getId());
			String username=SSUtil.getName();
			insu.setUser__username(username);
			insu.setInputDateTime(now);
			insu.setInsurDateTimeBegin(ExcelUtil.getString(row, 6));//保单开始时间
			insu.setInsurDateTimeEnd(ExcelUtil.getString(row, 7));//保单结束时间
			insu.setCarCode(ExcelUtil.getString(row, 8));//车牌号
			insu.setSumFee(Double.parseDouble(ExcelUtil.getString(row, 9)));//保费
			saleMDao.getInsuDao().saveOrUpdate(insu);
			sum++;
		}
		
		gotoMessagePage("成功导入"+sum+"张保单！","sale!returnToSaleList.action");
		
		return null;
	}
	/**
	 * 跳转到销售保单导入
	 * @return
	 */
	public String gotoInsuImprot(){
		return "gotoInsuImprot";
	}
	/**
	 * 跳转到销售页面
	 * @return
	 */
	public String gotoSale(){
		return "gotoSale";
	}
	/**
	 * 跳转到销售列表页面
	 * @return
	 */
	public String returnToSaleList(){
		listAllInsu();
		return "success";
	}
	/**
	 * 查询未答复留言
	 * @return
	 */
	public String noAnswerList(){
		page=feedbackDao.noAnswerList(page);
		return "noAnswerList";
	}
	/**
	 * 回复客户留言
	 * @return
	 */
	public String answerFeedback(){
		feedback.setUser__id(SSUtil.getUserDetails().getId());
		feedback.setIsOK("是");
		feedbackDao.updateSmart(feedback);
		//feedbackDao.ananswerFeedback(feedback);
		return noAnswerList();
	}
	/**
	 * 跳转到个人销售统计(SWF)
	 * @return
	 */
	public String goToSumInsu(){
		return gotoSWF("sumInsu");
	}
	/**
	 * 将查询列表独立出来
	 */
	private void listAllInsu(){
		String username=SSUtil.getName();
		if(insu==null) insu=new Insu();
		insu.setUser__username(username);
		if(host==null) host=new Carhost();
//		page=insuDao.findAllInsu(insu,host,page);
		
		Insu_Carhost i=new Insu_Carhost();
		i.setCarhost(host);
		i.setInsu(insu);
		page=saleMDao.joinpaging(i, page,new Like("insu.inputDateTime",insu.getInputDateTime()));
//		System.out.println(commonDao.joinpaging(i, page).getList().size());
	}
}
