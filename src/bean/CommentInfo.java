package bean;

import bean.User.UserCommonInfo;


public class CommentInfo {

	private int id;
	private String content;
	private String date;
//	private int domainid;
	private int userid;
//	private int type;
	/**
	 * 是否是回复的
	 */
	private int reply;
	private int floor;
//	private int targetid;
	private ReplyComment replyComment;
	private UserCommonInfo userInfo;
	
	
	
	
	public CommentInfo(int id, String content, String date, int reply, int floor) {
		super();
		this.id = id;
		this.content = content;
		this.date = date;
		this.reply = reply;
		this.floor = floor;
	}


//	public CommentInfo(int id, int domainid,int userid,String date, String content, 
//			int floor, int reply,  int targetid,int type) {
//		super();
//		this.id = id;
//		this.content = content;
//		this.date = date;
//		this.domainid = domainid;
//		this.userid = userid;
//		this.type = type;
//		this.reply = reply;
//		this.floor = floor;
//		this.targetid = targetid;
//	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


//	public int getDomainid() {
//		return domainid;
//	}
//
//
//	public void setDomainid(int domainid) {
//		this.domainid = domainid;
//	}


	public int getUserid() {
		return userid;
	}


	public void setUserid(int userid) {
		this.userid = userid;
	}


//	public int getType() {
//		return type;
//	}
//
//
//	public void setType(int type) {
//		this.type = type;
//	}


	public int getReply() {
		return reply;
	}


	public void setReply(int reply) {
		this.reply = reply;
	}


	public int getFloor() {
		return floor;
	}


	public void setFloor(int floor) {
		this.floor = floor;
	}


//	public int getTargetid() {
//		return targetid;
//	}
//
//
//	public void setTargetid(int targetid) {
//		this.targetid = targetid;
//	}


	public ReplyComment getReplyComment() {
		return replyComment;
	}


	public void setReplyComment(ReplyComment replyComment) {
		this.replyComment = replyComment;
	}


	public UserCommonInfo getUserInfo() {
		return userInfo;
	}


	public void setUserInfo(UserCommonInfo userInfo) {
		this.userInfo = userInfo;
	}


	public static class ReplyComment{
		private UserCommonInfo userInfo;
		private int floor;
		private String content;
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public UserCommonInfo getUserInfo() {
			return userInfo;
		}
		public void setUserInfo(UserCommonInfo userInfo) {
			this.userInfo = userInfo;
		}
		public ReplyComment(UserCommonInfo userInfo, String content,int floor) {
			super();
			this.userInfo = userInfo;
			this.content = content;
			this.floor = floor;
		}
		
		
		
	}
	
	
	
}
