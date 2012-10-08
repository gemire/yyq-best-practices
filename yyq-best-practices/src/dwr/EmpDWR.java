package dwr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sale.pojo.Carhost;
import sale.dao.CarhostDao;

@Service
public class EmpDWR {
	@Autowired
	private CarhostDao carhostDao;
	
	public List<Carhost> findCarhost(String username){
		List<Carhost> list=carhostDao.findCarhost(username);
//		for(Carhost host:list){
//			PrintUtil.printlnAllProperties(host);
//		}
		return list;
	}
}
