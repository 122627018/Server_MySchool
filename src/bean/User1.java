package bean;

/**
 * 用户实体：
 * 对应数据库和与移动端交互的数据格式
 * @author Administrator
 *
 */
public class User1 {
	private int id;
	private String name;
	private int officeid;
	private String description;
	private String pic;
	private OfficeUserInfo officeUserInfo;
	private LibUserInfo libUserInfo;
	private int libid;
	
	

	public User1(int id, String name, int officeid, String description,
			String pic,int libid, OfficeUserInfo officeUserInfo, LibUserInfo libUserInfo
			) {
		super();
		this.id = id;
		this.name = name;
		this.officeid = officeid;
		this.description = description;
		this.pic = pic;
		this.officeUserInfo = officeUserInfo;
		this.libUserInfo = libUserInfo;
		this.libid = libid;
	}

	public int getLibid() {
		return libid;
	}

	public void setLibid(int libid) {
		this.libid = libid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public OfficeUserInfo getOfficeUserInfo() {
		return officeUserInfo;
	}

	public void setOfficeUserInfo(OfficeUserInfo officeUserInfo) {
		this.officeUserInfo = officeUserInfo;
	}

	public LibUserInfo getLibUserInfo() {
		return libUserInfo;
	}

	public void setLibUserInfo(LibUserInfo libUserInfo) {
		this.libUserInfo = libUserInfo;
	}

	public static class OfficeUserInfo{
		
		public OfficeUserInfo(int id, String sex, String tname,
				String username, String password) {
			super();
			this.id = id;
			this.sex = sex;
			this.tname = tname;
			this.username = username;
			this.password = password;
		}
		private int id;
		private String sex;
		private String tname;
		private String username;
		private String password;
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
		
		
	}
	
	public static class LibUserInfo{
		private int id;
		private String username;
		private String password;
		
		public LibUserInfo(int id, String username, String password) {
			super();
			this.id = id;
			this.username = username;
			this.password = password;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
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
		
	}
}
