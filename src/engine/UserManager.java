package engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import exception.LibException;

import util.ConnectionUtil;
import util.HtmlUtil;
import util.MyHttpUtil;

import bean.User;
import bean.User.UserCommonInfo;
import bean.domain.User1;
import bean.domain.User1.OfficeUserInfo;
import bean.format.OfficeLogin;
import bean.format.OfficeLogin.OfficeBehave;
import bean.http.NetReceiverData;
import bean.http.NetSendData;


public class UserManager {
	
	public static<T> Map<String,Object> getResponseMap(int state,String error,String tempUrl
			,T infos){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("state", state);
		map.put("error", error);
		map.put("tempUrl", tempUrl);
		map.put("infos", infos);
		return map;
	}
	
	/**
	 * 连接正方系统验证账户是否正确
	 * @param username
	 * @param password
	 */
	public static Map<String,Object> loginFromNet(String username, String password) {
		//连接正方系统取得地址
		NetSendData firstData = new NetSendData();
		firstData.setUrl("http://210.38.162.117/");
		firstData.setHost("http://210.38.162.117/");
		NetReceiverData sendPost = MyHttpUtil.sendPost(firstData);
		if(sendPost.getContent() == null){
			return getResponseMap(100, "连接不上学校服务器", "", null);
		}
		//拿到临时地址
		String tempUrl = sendPost.getFromUrl().substring(sendPost.getFromUrl().indexOf("("), sendPost.getFromUrl().indexOf(")")+1);
//		System.out.println("sendPost.getFromUrl()="+sendPost.getFromUrl());
//		System.out.println(sendPost.getContent2String());
//		System.out.println("tempUrl="+tempUrl);
		//从数据库取得参数集合
		//http://210.38.162.116/#tempUrl#/default2.aspx
		Map<String, OfficeBehave> officeBehaves = getOfficeBehaves();
		OfficeBehave b = officeBehaves.get("login");
		String url = b.getUrl();
		String referer = b.getReferer();
		String pars = b.getPars();
	
		url = url.replaceAll("#tempUrl#", tempUrl);
		referer = url.replaceAll("#tempUrl#", referer);
		pars = pars.replaceAll("#username#", username).replaceAll("#password#", password);
//		System.out.println("url="+url+"--referer="+referer);
//		System.out.println("pars="+pars);
		NetSendData send = new NetSendData();
		send.setUrl(url);
		send.getHeaders().put("Referer", referer);
		send.setPars(pars);
		send.setHost("http://210.38.162.117/");
		//取得主页面的html
		NetReceiverData sendPost2 = MyHttpUtil.sendPost(send);
		//得到主页面的html后，要从html解析是否登录成功
		try {
			boolean result = HtmlUtil.parseOfficeMainHtml(sendPost2.getContent2String());
			if(result){
				//验证通过，从数据库取信息
				//还要判断是否是第一次登录
				User1 user = getUser(username);
				OfficeLogin result1 = new OfficeLogin(user, officeBehaves);
				return getResponseMap(200, "", tempUrl, result1);
			}else{
				//账号密码出错
				return getResponseMap(100, "账号或者密码出错", tempUrl, null);
			}
		} catch (LibException e) {
			//获取的html为空
			return getResponseMap(100, "连接不上学校服务器", tempUrl, null);
		}
		
	}
	
	/**
	 * 从数据库取得操作参数集合
	 * @return 
	 */
	public static Map<String, OfficeBehave> getOfficeBehaves() {
		Map<String,OfficeBehave> map = new HashMap<String, OfficeBehave>();
		try {
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("select pars,url,referer,behave from officepars");
			ResultSet rs = ps.executeQuery();
			OfficeBehave behave = null;
			while(rs.next()){
				behave = new OfficeBehave(rs.getString(1), rs.getString(2), rs.getString(3));
//				System.out.println(behave.toString());
				map.put(rs.getString(4), behave);
			}
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static User1 getUser(String username){
		User1 user = null;
		try {
			int officeId = 0;
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("select id from user_office where username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				officeId = rs.getInt(1);
				ps = conn.prepareStatement("select * from user_normal a,user_office b,libinfo c where officeid=?");
				ps.setInt(1, officeId);
				rs = ps.executeQuery();
				if(rs.next()){
					user = new User1(rs.getInt(1), rs.getString(3), rs.getInt(2), rs.getString(4)
							, rs.getString(5),rs.getInt(6),
							new OfficeUserInfo(rs.getInt(7), rs.getString(9), rs.getString(8)
									, rs.getString(10), rs.getString(11)),
							new User1.LibUserInfo(rs.getInt(12), rs.getString(13), rs.getString(14)));
				}
			}
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public static UserCommonInfo selectUserCommonInfo(int userid){
		UserCommonInfo info = null;
		try {
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("select * from user_normal where officeid=?");
			ps.setInt(1, userid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				info = new UserCommonInfo(rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

	public static User login(String username,String password) {
//		boolean result = false;
		User user = null;
		try {
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("select * from user_office where username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (password.equals(rs.getString(5))) {
					user = new User();
					user.setId(rs.getInt(1));
					user.setTname(rs.getString(2));
					user.setSex(rs.getString(3));
					user.setUsername(username);
					user.setPassword(password);
					 ps = conn.prepareStatement("select * from user_normal where officeid=?");
					 ps.setInt(1, user.getId());
					 rs = ps.executeQuery();
					 if(rs.next()){
						 user.getInfo().setOfficeid(rs.getInt(2));
						 user.getInfo().setName(rs.getString(3));
						 user.getInfo().setDescription(rs.getString(4));
						 user.getInfo().setPic(rs.getString(5));
					 }
				} else {
				}
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	
	
	/**
	 * 重新登录
	 * @param username
	 * @param password
	 * @return 
	 * tempUrl
	 * @throws LibException 
	 */
	public static  String RenewLogin(String username,String password) throws LibException{
		NetSendData firstData = new NetSendData();
		firstData.setUrl("http://210.38.162.117/");
		firstData.setHost("http://210.38.162.117/");
		NetReceiverData sendPost = MyHttpUtil.sendPost(firstData);
		if(sendPost.getContent() == null){
//			return getResponseMap(100, "连接不上学校服务器", "", null);
			throw new LibException(404, "连接不上学校服务器");
		}else{
			String tempUrl = sendPost.getFromUrl().substring(sendPost.getFromUrl().indexOf("("), sendPost.getFromUrl().indexOf(")")+1);
			return tempUrl;
		}
	}

	
}
