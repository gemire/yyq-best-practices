package authority.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yyq.dao.IdEntity;

/**
 * @author YYQ ORMTool
 */

@Entity
@Table(name = "authority", catalog = "yyq")
public class Authority extends IdEntity implements java.io.Serializable {
	private String authority;

	public Authority() {
	}

	@Column(name = "authority", nullable = false, length = 50)
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}