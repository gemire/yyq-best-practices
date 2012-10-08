package action;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.servlet.RequestDispatcher;

import lombok.Getter;
import lombok.Setter;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.yyq.ip.IPLocation;
import com.yyq.struts2.MyAction;
import com.yyq.util.HttpUtil;
import com.yyq.util.PinyinUtil;

@Results({
	@Result(name="goChatRoom",value="WEB-INF/web/common/message.jsp"),
	@Result(name="gotoWeather",value="WEB-INF/web/common/weather.jsp"),
})
public class CommonAction extends MyAction{
	@Getter@Setter
	private String swfPath;
	
	/**
	 * 跳转到"关于作者"Flash
	 * @return
	 */
	public String gotoAbout(){
		return gotoSWF("about");
	}
	/**
	 * 此方法已被弃用，被MyAction中的同名方法取代，因为使用此方法无法进行灵活的访问控制
	 * @return
	 */
	public String gotoSWF(){
		RequestDispatcher r=ServletActionContext.getServletContext().getRequestDispatcher(swfPath);
		try {
			r.forward(ServletActionContext.getRequest(), ServletActionContext.getResponse());
		} catch (Exception e) {
			log.error("跳转到SWF出错！");
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 跳转到在线聊天室
	 * @return
	 */
	public String goChatRoom(){
		return "goChatRoom";
	}
	
	/**
	 * 将用户登录过程中的地址查询和天气预报移到这里，
	 * 以提高用户登录的速度。
	 * @return
	 */
	public String getWeather(){
		String ip=getRequest().getRemoteAddr();
		if("0:0:0:0:0:0:0:1".equals(ip)){
			ip="127.0.0.1";//在win7下测试发现会返回ipv6格式，先这样应付下:)
		}
		IPLocation ipl = new IPLocation(ip);
		ip=ipl.getCountry();
		if("本机地址".equals(ip)){//如果是本机或是局域网用户则先去确定外网IP
			ip=HttpUtil.getHTMLStr("http://city.ip138.com/city0.asp");
			int begin=ip.indexOf("[");
			int end=ip.indexOf("]");
			ip=ip.substring(begin+1, end);
			ipl = new IPLocation(ip);
			ip=ipl.getCountry();
		}
		put("city",ip);//保存用户所在的城市
		
		String city=ipl.getCity(ip);//获得具体的城市名
		city=PinyinUtil.pinyin(city, null);//获得城市名的拼音，多音字是个大问题
		
		try{
		//获取google提供的天气预报，返回的是一个xml格式的字符串
		String str=HttpUtil.getHTMLStr("http://www.google.com.hk/ig/api?hl=zh_cn&weather="+city);
		
		Document doc=null;
		SAXReader reader = new SAXReader();
		try{
			doc=reader.read(new ByteArrayInputStream(str.getBytes("UTF-8")));//从字符串构件XML的Document
		}catch(Exception e){
			e.printStackTrace();
		}
		
		StringBuffer sb=new StringBuffer();
		//找到天气预报的节点
		List<Node> nodeList=doc.selectNodes("//weather/forecast_conditions");
		if(nodeList.size()!=0){
			Node today=nodeList.get(0);//今天的天气预报
			Element elm;
			
			elm=(Element) today.selectSingleNode("condition");
			sb.append("今日天气："+elm.attributeValue("data")+"；");
			
			elm=(Element) today.selectSingleNode("low");
			sb.append("最低气温："+elm.attributeValue("data")+"；");
			
			elm=(Element) today.selectSingleNode("high");
			sb.append("最高气温："+elm.attributeValue("data")+"；");
		}
		put("weather",sb.toString());//保存用户所在城市的天气
		}catch(Exception e){
			put("weather","由于google天气预报API已经停止服务，此功能现已无法使用！获取用户所在城市依然有效！");
		}
		
		return "gotoWeather";
	}
}
