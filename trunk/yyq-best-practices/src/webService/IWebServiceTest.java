package webService;

import javax.jws.WebService;

@WebService
public interface IWebServiceTest {
	public String sumInsuToday();
	public String posRSA(byte[] enText);
}
