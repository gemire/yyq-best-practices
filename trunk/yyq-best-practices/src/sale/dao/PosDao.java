package sale.dao;

import org.springframework.stereotype.Service;

import sale.pojo.Pos;

import com.yyq.dao.MyHibernateDaoSupport;

@Service(value="posDao")
public class PosDao extends MyHibernateDaoSupport<Pos>{

}