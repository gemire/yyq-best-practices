package sale.pojo;

import com.yyq.util.StringUtil;

public class CarhostHelper {
	/**
	 * 判断两个客户是否是同一客户
	 * @param host
	 * @param host_old
	 * @return
	 */
	public static boolean isEquals(Carhost host,Carhost host_old){
		if(StringUtil.isEquals(host.getCarHostName(),host_old.getCarHostName())&&
			StringUtil.isEquals(host.getCarHostCode(),host_old.getCarHostCode())&&
			StringUtil.isEquals(host.getAddress(),host_old.getAddress())&&
			StringUtil.isEquals(host.getSex(),host_old.getSex())&&
			host.getAge().equals(host_old.getAge())&&
			StringUtil.isEquals(host.getLinkTel(),host_old.getLinkTel())
		) return true;
		return false;
	}
	
}
