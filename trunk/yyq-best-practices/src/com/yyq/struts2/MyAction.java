package com.yyq.struts2;

import java.awt.Font;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

import com.yyq.jstl.Page;

/**
 * Struts2 Action基类
 * @author YYQ
 */
public class MyAction {
	protected final Logger log = Logger.getLogger(this.getClass());
	@Getter@Setter protected Page page; // 将分页参数定义在基类
	
	/**
	 * 从jsp到swf的跳转方法，使用方法是在具体的子类中将要跳转到的swf文件名传入即可，
	 * 这样可以进行各个角色的访问控制
	 * @param swfFile
	 * @return
	 */
	public String gotoSWF(String swfFile){
		RequestDispatcher r=ServletActionContext.getServletContext().getRequestDispatcher("/WEB-INF/web/flex/"+swfFile+".swf");
		try {
			r.forward(ServletActionContext.getRequest(), ServletActionContext.getResponse());
		} catch (Exception e) {
			System.out.println("跳转到SWF出错！");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 生成默认格式的JFreeChart饼图报表，并把filename放入request中，key为jfc
	 * @param title 图表的标题
	 * @param ds 数据集
	 * @param labelFormat 说明标签的显示格式
	 * @param width 图的宽度
	 * @param height 图的高度
	 */
	protected void defaultPieChart(String title,PieDataset ds,String labelFormat,int width,int height){
		Font font=new Font("宋体",Font.BOLD,15);
		JFreeChart jfc =ChartFactory.createPieChart(title,ds, true, true, true);
		setTitleAndLegendFont(jfc,font);
		PiePlot pie=(PiePlot)jfc.getPlot();
		pie.setLabelFont(font);
		pie.setLabelGenerator(new StandardPieSectionLabelGenerator(
			labelFormat,
			NumberFormat.getNumberInstance(),
			new DecimalFormat("0.00%"))
		);
		
		saveChart(jfc,width,height);
	}
	/**
	* 生成默认格式的JFreeChart饼图报表，并把filename放入request中，key为jfc
	* 图标的高度和宽度都是600
	 * @param title 图表的标题
	 * @param ds 数据集
	 * @param labelFormat 说明标签的显示格式
	 */
	protected void defaultPieChart(String title,PieDataset ds,String labelFormat){
		defaultPieChart(title,ds,labelFormat,600,600);
	}
	
	/**
	 * 生成默认格式的JFreeChart折线图报表，并把filename放入request中，key为jfc
	 * @param title 图表的标题
	 * @param domainAxisTitle x轴上的标题
	 * @param rangeAxisTitle y轴上的标题
	 * @param ds 数据集
	 * @param width 图的宽度
	 * @param height 图的高度
	 */
	protected void defaultLineChart(String title,String domainAxisTitle,String rangeAxisTitle,CategoryDataset ds,int width,int height){
		Font font=new Font("宋体",Font.BOLD,15);
		JFreeChart jfc = ChartFactory.createLineChart(title,//图表的标题
			domainAxisTitle,//x轴上的标题
			rangeAxisTitle,//y轴上的标题
			ds,//填充数据
			PlotOrientation.VERTICAL, // orientation
			true, // include legend
			true, // tooltips
			true // urls
		);
		setTitleAndLegendFont(jfc,font);
		CategoryPlot plot=(CategoryPlot)jfc.getPlot();
		plot.getDomainAxis().setLabelFont(font);//设置x轴上的标题的字体     
		plot.getRangeAxis().setLabelFont(font);//设置y轴坐标上的标题的字体

		saveChart(jfc,width,height);
	}
	/**
	 * 生成默认格式的JFreeChart折线图报表，并把filename放入request中，key为jfc
	 * 图标的高度和宽度都是600
	 * @param title 图表的标题
	 * @param domainAxisTitle x轴上的标题
	 * @param rangeAxisTitle y轴上的标题
	 * @param ds 数据集
	 */
	protected void defaultLineChart(String title,String domainAxisTitle,String rangeAxisTitle,CategoryDataset ds){
		defaultLineChart(title,domainAxisTitle,rangeAxisTitle,ds,600,600);
	}
	
	/**
	 * 设置图表的标题和说明
	 * @param jfc
	 * @param font
	 */
	private void setTitleAndLegendFont(JFreeChart jfc,Font font){
		jfc.getTitle().setFont(font);
		jfc.getLegend().setItemFont(font);
	}
	
	/**
	 * 保存JFreeChart图表
	 * @param jfc
	 */
	private void saveChart(JFreeChart jfc,int width,int height){
		try {
			String filename=ServletUtilities.saveChartAsJPEG(jfc, width, height, ServletActionContext.getRequest().getSession());
			put("jfc", filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 往request中存放变量
	 * @param key
	 * @param value
	 */
	protected void put(String key, Object value){
//		ActionContext.getContext().put(key, value);
		ServletActionContext.getRequest().setAttribute(key, value);
	}
	
	/**
	 * 从request中取出单个变量
	 * @param key
	 * @return
	 */
	protected String getParameter(String key){
		return ServletActionContext.getRequest().getParameter(key);
	}
	
	/**
	 * 从request中取出变量数组
	 * @param key
	 * @return
	 */
	protected String[] getParameterValues(String key){
		return ServletActionContext.getRequest().getParameterValues(key);
	}
	
	protected HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	
	/**
	 * 默认的提示消息
	 */
	protected void showMessage(){
		showMessage("操作成功！");
	}
	
	/**
	 * 消息提示方法
	 * @param message
	 */
	protected void showMessage(String message){
		put("message",message);
	}
	
	/**
	 * 将execute()设为final，以阻止子类使用
	 * @return
	 */
	protected final String execute(){
		return null;
	}
	/**
	 * 跳转到消息提示页面
	 * @param message
	 * @param backtoPage
	 * @return
	 */
	protected final String gotoMessagePage(String message,String backtoPage){
		put("message", message);
		put("backtoPage", "/YYQ/"+backtoPage);
		RequestDispatcher r=ServletActionContext.getServletContext().getRequestDispatcher("/message.jsp");
		try {
			r.forward(ServletActionContext.getRequest(), ServletActionContext.getResponse());
		} catch (Exception e) {
			System.out.println("跳转到消息提示页面出错！");
			e.printStackTrace();
		}
		return null;
	}
	protected final void addParam1(String param1name,String param1value){
		put("param1name", param1name);
		put("param1value", param1value);
	}
}
