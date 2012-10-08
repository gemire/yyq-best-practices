package authority.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yyq.dao.IdEntity;

/**
 * @author YYQ ORMTool
 */

@Entity
@Table(name = "authorityset", catalog = "yyq")
public class Authorityset extends IdEntity implements java.io.Serializable {
	private String user__id;
	private String authority__id;

	public Authorityset() {
	}

	@Column(name = "user__id", nullable = false, length = 22)
	public String getUser__id() {
		return user__id;
	}

	public void setUser__id(String user__id) {
		this.user__id = user__id;
	}

	@Column(name = "authority__id", nullable = false, length = 22)
	public String getAuthority__id() {
		return authority__id;
	}

	public void setAuthority__id(String authority__id) {
		this.authority__id = authority__id;
	}

}