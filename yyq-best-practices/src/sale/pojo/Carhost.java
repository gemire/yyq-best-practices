package sale.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yyq.dao.IdEntity;

/**
 * @author YYQ ORMTool
 */

@Entity
@Table(name = "carhost", catalog = "yyq")
public class Carhost extends IdEntity implements java.io.Serializable {
	private String carHostName;
	private String carHostCode;
	private String sex;
	private Integer age;
	private String address;
	private String linkTel;

	public Carhost() {
	}

	@Column(name = "carHostName", nullable = false, length = 50)
	public String getCarHostName() {
		return carHostName;
	}

	public void setCarHostName(String carHostName) {
		this.carHostName = carHostName;
	}

	@Column(name = "carHostCode", nullable = false, length = 50)
	public String getCarHostCode() {
		return carHostCode;
	}

	public void setCarHostCode(String carHostCode) {
		this.carHostCode = carHostCode;
	}

	@Column(name = "sex", nullable = false, length = 1)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "age", nullable = false)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "address", length = 200)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "linkTel", length = 20)
	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

}