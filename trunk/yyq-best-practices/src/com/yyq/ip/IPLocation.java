package com.yyq.ip;

/**
 * 
 * @category 用来封装ip相关信息，目前只有两个字段，ip所在的国家和地区
 */

public class IPLocation {
	private String country;
	private String area;

	public IPLocation() {
		country = Message.unknown_country;
		area = Message.unknown_area;
	}

	public IPLocation(String ip) {
		byte[] bytes = Util.getIpByteArrayFromString(ip);
		IPSeeker ipSeeker = new IPSeeker();
		ipSeeker.getIPLocation(bytes, this);
	}
	
	public String getCity(String country){
		int i=country.indexOf("省");
		if(i!=-1){
			country=country.substring(i+1);
		}
		if("市".equals(country.substring(country.length()-1))){//这边用charAt不行
			country=country.substring(0, country.length()-1);
		}
		return country;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		// 如果为局域网，纯真IP地址库的地区会显示CZ88.NET,这里把它去掉
//		if (area.trim().equals("CZ88.NET")) {
//			this.area = "本机或本网络";
//		} else {
		this.area = area;
//		}
	}
}
