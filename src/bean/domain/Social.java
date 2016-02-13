package bean.domain;

import bean.User.UserCommonInfo;

public class Social {

	private int id;
	private int userid;
	private int likes;
	private String title;
	private String detail;
	private String date;
	private String pic;
	private UserCommonInfo userInfo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public UserCommonInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserCommonInfo userInfo) {
		this.userInfo = userInfo;
	}
	public Social(int id, int userid, int likes, String title, String detail,
			String date, String pic) {
		super();
		this.id = id;
		this.userid = userid;
		this.likes = likes;
		this.title = title;
		this.detail = detail;
		this.date = date;
		this.pic = pic;
	}
	
	
}
