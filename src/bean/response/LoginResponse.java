package bean.response;

import java.util.HashMap;
import java.util.Map;

import bean.domain.web.LibUserInfo;

@Deprecated
public class LoginResponse {
	
	private String cookie;
	/**
	 * 参数集合
	 */
	private Map<String,Behave> behaves = new HashMap<String, LoginResponse.Behave>();
	
	private LibUserInfo userinfo;
	
	
	

	
	public LibUserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(LibUserInfo userinfo) {
		this.userinfo = userinfo;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public Map<String, Behave> getBehaves() {
		return behaves;
	}

	public void setBehaves(Map<String, Behave> behaves) {
		this.behaves = behaves;
	}


	public static class Behave{
		private String pars;
		private String url;
		public String getPars() {
			return pars;
		}
		public void setPars(String pars) {
			this.pars = pars;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
	}
}
