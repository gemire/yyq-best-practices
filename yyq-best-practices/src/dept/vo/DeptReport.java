package dept.vo;

/**
 * 部门报表数据VO类
 * @author Administrator
 */
public class DeptReport {
	private String username;
	private String name;
	private int sumInsu;
	private double sumFee;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSumInsu() {
		return sumInsu;
	}
	public void setSumInsu(int sumInsu) {
		this.sumInsu = sumInsu;
	}
	public double getSumFee() {
		return sumFee;
	}
	public void setSumFee(double sumFee) {
		this.sumFee = sumFee;
	}
}
