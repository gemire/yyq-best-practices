package sale.dao;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyq.dao.ModuleDao;

@Service(value="saleMDao")
public class SaleMDao extends ModuleDao{
	@Autowired@Getter@Setter
	private CarhostDao carhostDao;
	@Autowired@Getter@Setter
	private InsuDao insuDao;
	@Autowired@Getter@Setter
	private PosDao posDao;
	
}
