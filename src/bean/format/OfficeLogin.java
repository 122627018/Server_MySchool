package bean.format;

import java.util.HashMap;
import java.util.Map;

import bean.domain.User1;

/**
 * 实体：
 * 发送给移动端的数据格式
 * @author Administrator
 *
 */
public class OfficeLogin {

	private User1 userinfo;
	private Map<String,OfficeBehave> behaves = new HashMap<String, OfficeBehave>();
	
	
	public User1 getUserinfo() {
		return userinfo;
	}


	public void setUserinfo(User1 userinfo) {
		this.userinfo = userinfo;
	}


	public Map<String, OfficeBehave> getBehaves() {
		return behaves;
	}


	public void setBehaves(Map<String, OfficeBehave> behaves) {
		this.behaves = behaves;
	}
	
	


	public OfficeLogin(User1 userinfo, Map<String, OfficeBehave> behaves) {
		super();
		this.userinfo = userinfo;
		this.behaves = behaves;
	}

	/**
	 * 行为，对应数据库
	 * @author Administrator
	 *
	 */
	public static class OfficeBehave{
		private String pars;
		private String url;
		private String referer;
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
		public String getReferer() {
			return referer;
		}
		public void setReferer(String referer) {
			this.referer = referer;
		}
		public OfficeBehave(String pars, String url, String referer) {
			super();
			this.pars = pars;
			this.url = url;
			this.referer = referer;
		}
		@Override
		public String toString() {
			return "OfficeBehave [pars=" + pars + ", url=" + url + ", referer="
					+ referer + "]";
		}
		
		
		
	}
}
