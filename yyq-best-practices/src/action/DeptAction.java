package action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import lombok.Getter;
import lombok.Setter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;

import dept.dao.DeptDao;
import dept.vo.DeptReport;

import com.yyq.struts2.MyAction;
import com.yyq.util.DateUtil;
import com.yyq.util.POIUtil;
import com.yyq.util.SSUtil;

@Results({
    @Result(name="success",value="WEB-INF/web/company/company.jsp")
})
public class DeptAction extends MyAction{
	@Autowired@Getter@Setter
	private DeptDao deptDao;
	@Autowired@Getter@Setter
	private DataSource dataSource;
	
	/**
	 * 查找部门销售报表数据
	 */
	public String findDeptReport(){
		String username=SSUtil.getName();
		List<DeptReport> deptReportList=deptDao.findDeptReport(username);
		put("deptReportList", deptReportList);
		
		//部门销售饼图
		DefaultPieDataset ds = new DefaultPieDataset();
		for(DeptReport dr:deptReportList){
			ds.setValue(dr.getName(), dr.getSumFee());
		}
		defaultPieChart("部门销售情况统计图", ds, "{0}--{1}元{2}", 480, 480);
		
		return "success";
	}
	/**
	 * 此方法从数据库中查询数据，然后生成Excel文件输出到客户端
	 */
	public String getExcel(){
		List<DeptReport> deptReportList=deptDao.findDeptReport(SSUtil.getName());
		
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("application/vnd.ms-excel;charset=gb2312"); // 设置文件关联类型
		
		String filename="部门销售报表";
		try {
			filename=new String(filename.getBytes("gb2312"),"iso8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String date=DateUtil.getDate();
		filename+="_"+date;
		response.addHeader("Content-Disposition","attachment; filename="+filename+".xls"); // 设置文件名
		
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet=workbook.createSheet("部门销售报表");
		sheet.createFreezePane(0, 3); // 固定住前三行
		HSSFRow row;
		HSSFCell cell;
		
		/* 单元格风格定义开始  */
		HSSFFont biaoti_font = workbook.createFont(); // 设置标题的字体
		biaoti_font.setFontHeight((short) (256 * 2));
		biaoti_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 粗体
		biaoti_font.setColor(HSSFColor.RED.index);

		HSSFCellStyle biaoti_cellStyle = workbook.createCellStyle(); // 设置标题的风格
		biaoti_cellStyle.setFont(biaoti_font);
		biaoti_cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		biaoti_cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中

		HSSFFont col_font = workbook.createFont();
		col_font.setColor(HSSFColor.ROYAL_BLUE.index);
		col_font.setFontHeight((short) (256 * 1));
		col_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		HSSFCellStyle firstRow_cellStyle = workbook.createCellStyle(); // 行首的风格
		firstRow_cellStyle.setFont(col_font);
		firstRow_cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		firstRow_cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		HSSFCellStyle otherRow_cellStyle = workbook.createCellStyle();
		otherRow_cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		/* 单元格风格定义结束  */
		
		
		row=sheet.createRow((short)0);
		row.setHeight((short) (256 * 1.5));
		cell = row.createCell(0);
		String deptName=deptDao.getDeptNamebyUsername(SSUtil.getName());
		cell.setCellValue(deptName+"部门销售报表");
		cell.setCellStyle(biaoti_cellStyle);

		row = sheet.createRow((short) 1);
		row.setHeight((short) (256 * 1.5));

		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 4)); // 合并单元格
		
		row=sheet.createRow((short)2); // 创建首行
		
		cell=row.createCell(0);
		cell.setCellValue("序号");
		
		cell=row.createCell(1);
		cell.setCellValue("用户名");
		
		cell=row.createCell(2);
		cell.setCellValue("员工姓名");
		
		cell=row.createCell(3);
		cell.setCellValue("销售量");
		
		cell=row.createCell(4);
		cell.setCellValue("销售额");
		
		POIUtil.setRowEveryCellStyle(row, firstRow_cellStyle); // 应用风格
		
		DeptReport deptReport;
		for(int i=0;i<deptReportList.size();i++){
			deptReport=deptReportList.get(i);
			row=sheet.createRow((i+3));
			cell=row.createCell(0);
			cell.setCellValue(i+1);
			
			cell=row.createCell(1);
			cell.setCellValue(deptReport.getUsername());
			
			cell=row.createCell(2);
			cell.setCellValue(deptReport.getName());
			
			cell=row.createCell(3);
			cell.setCellValue(deptReport.getSumInsu());
			
			cell=row.createCell(4);
			cell.setCellValue(deptReport.getSumFee());
			
			POIUtil.setRowEveryCellStyle(row, otherRow_cellStyle); // 应用风格
		}
		
		for(int i=0;i<5;i++){
			sheet.setColumnWidth(i, 256 * 30); // 设置列宽
		}
		
		OutputStream os;
		try {
			os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 向客户端生成PDF格式的部门销售报表
	 * @return
	 * @throws SQLException
	 */
	public String getPDF() throws SQLException{
		Connection conn=dataSource.getConnection();
		
		String filePath=ServletActionContext.getServletContext().getRealPath("")+"\\ireport\\deptReport.jasper";
		String deptName=deptDao.getDeptNamebyUsername(SSUtil.getName());
		//为ireport里的$P{}赋值
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("username",SSUtil.getName());
		parameters.put("deptName",deptName);
		
		byte[] bytes=null;
		try{
		// fill
		//JasperPrint jasperPrint = JasperFillManager.fillReport(filePath,parameters,conn);
		
		// 生成pdf
		bytes = JasperRunManager.runReportToPdf(filePath,parameters,conn);
		} catch (JRException e) {
			e.printStackTrace();
		}
		
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("application/pdf;charset=gb2312"); // 设置文件关联类型
		
		String date=DateUtil.getDate();
		String filename=deptName+"部门销售报表";
		try {
			filename=new String(filename.getBytes("gb2312"),"iso8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		filename+="_"+date;
		response.addHeader("Content-Disposition","attachment; filename="+filename+".pdf"); // 设置文件名
		
		OutputStream os;
		try {
			os = response.getOutputStream();
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes,0,bytes.length);
			os.flush();
			os.close();
			conn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
