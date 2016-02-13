package bean;

public class User {

	private int id;
	private String sex;
	private String tname;
	private String username;
	private String password;
//	private int infoId;
	public UserCommonInfo info = new UserCommonInfo();
	
	public static class UserCommonInfo{
//		private int infoId;
		private String name;
		private int officeid;
		private String description;
		private String pic;
		
		
//		public int getInfoId() {
//			return infoId;
//		}
//		public void setInfoId(int infoId) {
//			this.infoId = infoId;
//		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getOfficeid() {
			return officeid;
		}
		public void setOfficeid(int officeid) {
			this.officeid = officeid;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getPic() {
			return pic;
		}
		public void setPic(String pic) {
			this.pic = pic;
		}
		public UserCommonInfo(int officeid,String name,  String description,
				String pic) {
			super();
			this.name = name;
			this.officeid = officeid;
			this.description = description;
			this.pic = pic;
		}
		public UserCommonInfo() {
			super();
		}
		
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserCommonInfo getInfo() {
		return info;
	}

	public void setInfo(UserCommonInfo info) {
		this.info = info;
	}
	
}
