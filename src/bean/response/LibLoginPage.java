package bean.response;

@Deprecated
public class LibLoginPage {

	private String cookie;
	private byte[] picByte;
	
	public byte[] getPicByte() {
		return picByte;
	}
	public void setPicByte(byte[] bs) {
		this.picByte = bs;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
}
