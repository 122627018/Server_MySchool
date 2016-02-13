package engine;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import util.ConnectionUtil;
import util.HtmlUtil;
import util.MyHttpWebUtil;
import bean.format.LibBorrowState;
import bean.format.Login;
import bean.format.Login.Behave;
import bean.http.NetReceiverData;
import bean.http.NetSendData;
import bean.http.ResponseData;
import bean.response.LibLoginPage;
import bean.response.LoginResponse;
import exception.LibException;

@SuppressWarnings("deprecation")
public class LibManager {
	
	public static Map<String,Object> processException(LibException e,String cookie){
		switch (e.getState()) {
		case 1://登录过期
//			System.out.println("登录会话过期");
			return getLibCodePic1("",true);
		case 4://获取的html为空，无法连接学校服务器
			return getResponseMap(4, e.getMessage(), cookie, null, null);
		case 6://未知错误
			return getResponseMap(6, e.getMessage(), cookie, null, null);
		}
		return null;
	}
	
	public static<T> Map<String,Object> getResponseMap(int state,String error,String cookie
			,byte[] codeByte,T infos){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("state", state);
		map.put("error", error);
		map.put("cookie", cookie);
		map.put("codeByte", codeByte);
		map.put("infos", infos);
		return map;
	}
	
	/**
	 * 获取借阅情况
	 * 
	 * @param url
	 * @param cookie
	 * @param pars
	 */
	public static Map<String,Object> getBorrowState1(String url,
			String cookie, String pars) {
		LibBorrowState item = new LibBorrowState();
		NetSendData send = new NetSendData();
		send.setUrl(url);
		send.getHeaders().put("Cookie", cookie);
		NetReceiverData data = MyHttpWebUtil.sendGet(send);
		try {
			HtmlUtil.FZBorrowStatePage(item, new String(data.getContent(),
					"utf-8"));
			return getResponseMap(200, "", data.getHeaders().get("Cookie"), null, item);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (LibException e) {
			return processException(e, cookie);
//			return getResponseMap(1, e.getMessage(), cookie, null, null);
		}
		return null;
	}

	/**
	 * 获取借阅情况
	 * 
	 * @param url
	 * @param cookie
	 * @param pars
	 */
	@Deprecated
	public static ResponseData<LibBorrowState> getBorrowState(String url,
			String cookie, String pars) {
		ResponseData<LibBorrowState> result = new ResponseData<LibBorrowState>();
		LibBorrowState item = new LibBorrowState();
		NetSendData send = new NetSendData();
		send.setUrl(url);
		send.getHeaders().put("Cookie", cookie);
		NetReceiverData data = MyHttpWebUtil.sendGet(send);
		try {
			HtmlUtil.FZBorrowStatePage(item, new String(data.getContent(),
					"utf-8"));
//			item.setCookie(data.getHeaders().get("Cookie"));
			result.setObj(item);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (LibException e) {
			result.setError(e.getMessage());
			result.setState(e.getState());
		}
		return result;
	}
	
	/**
	 * 移动端获取获取验证码
	 * 
	 * @return 需要返回pic的流和cookie
	 */
	public static Map<String,Object> getLibCodePic1(String cookie,boolean isOutLogin)
			{
//		LibLoginPage page = new LibLoginPage();
		
		NetSendData send = new NetSendData();
		send.setUrl("http://210.38.162.2/OPAC/login.aspx?ReturnUrl=/opac/user/userinfo.aspx");
		NetReceiverData data = MyHttpWebUtil.sendGet(send);
		try {
			String libCodePicUrl = HtmlUtil.getLibCodePicUrl(data
					.getContent2String());
			NetSendData getCode = new NetSendData();
			getCode.setUrl(libCodePicUrl);
			String cookie1 = data.getHeaders().get("Cookie");
//			page.setCookie(data.getHeaders().get("Cookie"));
			getCode.getHeaders().put("Cookie", data.getHeaders().get("Cookie"));
			NetReceiverData receiverData = MyHttpWebUtil.sendGet(getCode);
			byte[] picCode = receiverData.getContent();
			if(isOutLogin){
				return getResponseMap(1, "登录会话过期", cookie1, picCode, null);
			}else{
				return getResponseMap(200, "", cookie1, picCode, null);
			}
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (LibException e) {
			return getResponseMap(e.getState(), e.getMessage(), cookie, null, null);
		}
		return null;
	}

	/**
	 * 移动端获取获取验证码
	 * 
	 * @return 需要返回pic的流和cookie
	 */
	@Deprecated
	public static ResponseData<LibLoginPage> getLibCodePic(String cookie)
			throws UnsupportedEncodingException {
		ResponseData<LibLoginPage> result = new ResponseData<LibLoginPage>();
		LibLoginPage page = new LibLoginPage();
		
		NetSendData send = new NetSendData();
		send.setUrl("http://210.38.162.2/OPAC/login.aspx?ReturnUrl=/opac/user/userinfo.aspx");
		NetReceiverData data = MyHttpWebUtil.sendGet(send);
		try {
			String libCodePicUrl = HtmlUtil.getLibCodePicUrl(data
					.getContent2String());
			NetSendData getCode = new NetSendData();
			getCode.setUrl(libCodePicUrl);
			page.setCookie(data.getHeaders().get("Cookie"));
			getCode.getHeaders().put("Cookie", data.getHeaders().get("Cookie"));
			NetReceiverData receiverData = MyHttpWebUtil.sendGet(getCode);
			page.setPicByte(receiverData.getContent());
			result.setObj(page);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (LibException e) {
			result.setState(e.getState());
			result.setError(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 执行登录操作
	 * 
	 * @param username
	 * @param password
	 * @param cookie
	 * @param code
	 * 
	 * @return 返回需要包括的东西： userinfo 各种功能所需要的参数和地址 cookie
	 */
	public static Map<String,Object> Login1(String username,
			String password, String cookie, String code) {
		// 从数据库取得参数集合封装
		// 取出登录这个操作所需要的参数和url
		// 把参数中的username这些字段替换

		// 1.先生成一个返回的实例，对应的泛型是LoginResponse
		// 2.要返回给移动端的数据封装成的一个实体：
		// 返回需要包括的东西： userinfo 各种功能所需要的参数和地址 cookie
//		LoginResponse response = new LoginResponse();
		Login login = new Login();

		// 3.从数据库取得所需要的全部参数集合
		login.setBehaves(getLoginPars());
		// System.out.println(getLoginPars().size());
		// 4.刚从数据库取得的参数这次操作所需要的参数和url，然后替换参数中需要用户发送过来的某些参数
		Behave behave = login.getBehaves().get("login");
		String loginPars = behave.getPars();
		String loginUrl = behave.getUrl();
		loginPars = loginPars.replaceAll("#username#", username)
				.replaceAll("#password#", password).replaceAll("#code#", code);
		// System.out.println("loginUrl="+loginUrl);

		// 5.封装请求，设置头部，url，参数
		NetSendData loginToSendData = new NetSendData();
		loginToSendData.setUrl(loginUrl);
		// 设置登录操作的一些头部
		loginToSendData.getHeaders().put("Cookie", cookie);
		loginToSendData.setHost(login.getBehaves().get("host").getUrl());
		loginToSendData.setPars(loginPars);

		// 6.发送请求，取得服务器返回的数据对应的实体
		NetReceiverData receiverData = MyHttpWebUtil.sendPost(loginToSendData);
		// 7.要通过receiverData来判断是否登录成功
		try {
			HtmlUtil.FZHasLoginPage(login,
					new String(receiverData.getContent(), "utf-8"));
			// 8.把服务器回复的response封装好的实体进行解析，取得各种信息再次封装，例如个人信息
//			response.setCookie(receiverData.getHeaders().get("Cookie"));
			String tempCookie = receiverData.getHeaders().get("Cookie");
//			result.setObj(response);
			return getResponseMap(200, "", tempCookie, null, login);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (LibException e) {
//			result.setError(e.getMessage());
//			result.setState(e.getState());
			return getResponseMap(e.getState(), e.getMessage(), cookie, null, null);
		}

		return null;
	}

	/**
	 * 执行登录操作
	 * 
	 * @param username
	 * @param password
	 * @param cookie
	 * @param code
	 * 
	 * @return 返回需要包括的东西： userinfo 各种功能所需要的参数和地址 cookie
	 */
	@Deprecated
	public static ResponseData<LoginResponse> Login(String username,
			String password, String cookie, String code) {
		// 从数据库取得参数集合封装
		// 取出登录这个操作所需要的参数和url
		// 把参数中的username这些字段替换

		// 1.先生成一个返回的实例，对应的泛型是LoginResponse
		ResponseData<LoginResponse> result = new ResponseData<LoginResponse>();
		// 2.要返回给移动端的数据封装成的一个实体：
		// 返回需要包括的东西： userinfo 各种功能所需要的参数和地址 cookie
//		LoginResponse response = new LoginResponse();

		// 3.从数据库取得所需要的全部参数集合
//		response.setBehaves(getLoginPars());
		// System.out.println(getLoginPars().size());
		// 4.刚从数据库取得的参数这次操作所需要的参数和url，然后替换参数中需要用户发送过来的某些参数
//		Behave behave = response.getBehaves().get("login");
//		String loginPars = behave.getPars();
//		String loginUrl = behave.getUrl();
//		loginPars = loginPars.replaceAll("#username#", username)
//				.replaceAll("#password#", password).replaceAll("#code#", code);
//		// System.out.println("loginUrl="+loginUrl);
//
//		// 5.封装请求，设置头部，url，参数
//		NetSendData loginToSendData = new NetSendData();
//		loginToSendData.setUrl(loginUrl);
//		// 设置登录操作的一些头部
//		loginToSendData.getHeaders().put("Cookie", cookie);
//		loginToSendData.setHost(response.getBehaves().get("host").getUrl());
//		loginToSendData.setPars(loginPars);
//
//		// 6.发送请求，取得服务器返回的数据对应的实体
//		NetReceiverData receiverData = MyHttpWebUtil.sendPost(loginToSendData);
//		// 7.要通过receiverData来判断是否登录成功
//		try {
//			HtmlUtil.FZHasLoginPage(result.getObj(),
//					new String(receiverData.getContent(), "utf-8"));
//			// 8.把服务器回复的response封装好的实体进行解析，取得各种信息再次封装，例如个人信息
//			response.setCookie(receiverData.getHeaders().get("Cookie"));
//			result.setObj(response);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (LibException e) {
//			result.setError(e.getMessage());
//			result.setState(e.getState());
//		}

		return result;
	}

	public static Map<String, Behave> getLoginPars() {
		Map<String, Behave> map = new HashMap<String, Behave>();
		try {
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("select * from libpars");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Behave b = new Behave();
				b.setPars(rs.getString(3));
				b.setUrl(rs.getString(4));
				map.put(rs.getString(2), b);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
