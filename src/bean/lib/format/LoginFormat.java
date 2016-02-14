package bean.lib.format;

import bean.lib.LibUserInfo;

/**
 * 图书馆登录操作的数据格式
 * @author Administrator
 *
 */
public class LoginFormat {
	private LibUserInfo userinfo;

	public LibUserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(LibUserInfo userinfo) {
		this.userinfo = userinfo;
	}
	
}
