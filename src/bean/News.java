package bean;

public class News {

	private int id;
	private String title;
//	private String body;
	private String data;
	private String url = "sd";
	private String picurl = "sd";
//	private int type;
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
//	public String getBody() {
//		return body;
//	}
//	public int getType() {
//		return type;
//	}
//	public void setType(int type) {
//		this.type = type;
//	}
//	public void setBody(String body) {
//		this.body = body;
//	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
	public News(int id, String title, String data, String picurl,String url) {
		super();
		this.id = id;
		this.title = title;
		this.data = data;
		this.url = url;
		this.picurl = picurl;
	}
//	public News(int id, String title, String body,    String data,
//			int type,String picurl,String url) {
//		super();
//		this.id = id;
//		this.title = title;
//		this.body = body;
//		this.data = data;
//		this.type = type;
//		this.url = url;
//		this.picurl = picurl;
//	}
	
//	public News(String title, String body, String data, String url,
//			String picurl, int type) {
//		super();
//		this.title = title;
//		this.body = body;
//		this.data = data;
//		this.url = url;
//		this.picurl = picurl;
//		this.type = type;
//	}

	
}
