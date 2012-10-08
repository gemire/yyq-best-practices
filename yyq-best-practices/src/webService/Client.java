package webService;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * 从Java客户端调用WebService
 * @author YYQ
 */
public class Client {
	public static void main(String[] args) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IWebServiceTest.class);
		factory.setAddress("http://localhost:8080/YYQ/services/WebServiceTest");
		IWebServiceTest webServiceTest = (IWebServiceTest)factory.create();
		String reply = webServiceTest.sumInsuToday();
		System.out.println(reply);
	}
}
