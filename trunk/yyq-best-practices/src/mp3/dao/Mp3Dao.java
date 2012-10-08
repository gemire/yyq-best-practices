package mp3.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import mp3.pojo.Mp3;

import com.yyq.dao.MyHibernateDaoSupport;

@Service(value="mp3Dao")
public class Mp3Dao extends MyHibernateDaoSupport<Mp3>{
	public List<Mp3> findMp3(String dir){
		return getHibernateTemplate().find("from Mp3 where dir=?",dir);
	}
}