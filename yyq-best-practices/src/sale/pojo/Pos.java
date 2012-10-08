package sale.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yyq.dao.IdEntity;

/**
 * @author YYQ ORMTool
 */

@Entity
@Table(name = "pos", catalog = "yyq")
public class Pos extends IdEntity implements java.io.Serializable {
	private String user__id;
	private String name;
	private String address;
	private Double money;
	private String user__username;
	private String dataTime;

	public Pos() {
	}

	@Column(name = "user__id", nullable = false, length = 22)
	public String getUser__id() {
		return user__id;
	}

	public void setUser__id(String user__id) {
		this.user__id = user__id;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "address", length = 200)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "money", nullable = false)
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "user__username", nullable = false, length = 50)
	public String getUser__username() {
		return user__username;
	}

	public void setUser__username(String user__username) {
		this.user__username = user__username;
	}

	@Column(name = "dataTime", nullable = false, length = 19)
	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

}