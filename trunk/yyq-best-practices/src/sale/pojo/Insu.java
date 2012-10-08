package sale.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yyq.dao.IdEntity;

/**
 * @author YYQ ORMTool
 */

@Entity
@Table(name = "insu", catalog = "yyq")
public class Insu extends IdEntity implements java.io.Serializable {
	private String user__username;
	private String inputDateTime;
	private String insurDateTimeBegin;
	private String insurDateTimeEnd;
	private String carCode;
	private Double sumFee;
	private String carhost__id;

	public Insu() {
	}

	@Column(name = "user__username", nullable = false, length = 50)
	public String getUser__username() {
		return user__username;
	}

	public void setUser__username(String user__username) {
		this.user__username = user__username;
	}

	@Column(name = "inputDateTime", nullable = false, length = 45)
	public String getInputDateTime() {
		return inputDateTime;
	}

	public void setInputDateTime(String inputDateTime) {
		this.inputDateTime = inputDateTime;
	}

	@Column(name = "insurDateTimeBegin", nullable = false, length = 45)
	public String getInsurDateTimeBegin() {
		return insurDateTimeBegin;
	}

	public void setInsurDateTimeBegin(String insurDateTimeBegin) {
		this.insurDateTimeBegin = insurDateTimeBegin;
	}

	@Column(name = "insurDateTimeEnd", nullable = false, length = 45)
	public String getInsurDateTimeEnd() {
		return insurDateTimeEnd;
	}

	public void setInsurDateTimeEnd(String insurDateTimeEnd) {
		this.insurDateTimeEnd = insurDateTimeEnd;
	}

	@Column(name = "carCode", nullable = false, length = 45)
	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	@Column(name = "sumFee", nullable = false)
	public Double getSumFee() {
		return sumFee;
	}

	public void setSumFee(Double sumFee) {
		this.sumFee = sumFee;
	}

	@Column(name = "carhost__id", nullable = false, length = 22)
	public String getCarhost__id() {
		return carhost__id;
	}

	public void setCarhost__id(String carhost__id) {
		this.carhost__id = carhost__id;
	}

}