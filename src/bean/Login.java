package bean;

import java.util.HashMap;
import java.util.Map;

import bean.lib.LibUserInfo;

public class Login {
	/**
	 * 参数集合
	 */
	private Map<String,Behave> behaves = new HashMap<String, Behave>();
	
	private LibUserInfo userinfo;
	
	
	


	public Map<String, Behave> getBehaves() {
		return behaves;
	}





	public void setBehaves(Map<String, Behave> behaves) {
		this.behaves = behaves;
	}





	public LibUserInfo getUserinfo() {
		return userinfo;
	}





	public void setUserinfo(LibUserInfo userinfo) {
		this.userinfo = userinfo;
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
