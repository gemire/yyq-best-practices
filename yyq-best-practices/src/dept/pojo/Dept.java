package dept.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yyq.dao.IdEntity;

/**
 * @author YYQ ORMTool
 */

@Entity
@Table(name = "dept", catalog = "yyq")
public class Dept extends IdEntity implements java.io.Serializable {
	private String name;
	private String user__id;

	public Dept() {
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "user__id", length = 22)
	public String getUser__id() {
		return user__id;
	}

	public void setUser__id(String user__id) {
		this.user__id = user__id;
	}

}