package administrator.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yyq.dao.IdEntity;

/**
 * @author YYQ ORMTool
 */

@Entity
@Table(name = "user", catalog = "yyq")
public class User extends IdEntity implements java.io.Serializable {
	private String username;
	private String password;
	private Integer userType;
	private String name;
	private Double money;
	private String dept__id;
	private String enabled;

	public User() {
	}

	@Column(name = "username", nullable = false, length = 50)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 50)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "userType", nullable = false)
	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "money")
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "dept__id", nullable = false, length = 22)
	public String getDept__id() {
		return dept__id;
	}

	public void setDept__id(String dept__id) {
		this.dept__id = dept__id;
	}

	@Column(name = "enabled", nullable = false, length = 5)
	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

}