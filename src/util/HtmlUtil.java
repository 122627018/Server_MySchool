package util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import exception.LibException;
import exception.OutOfLoginException;

import bean.lib.BookBorrowedState;
import bean.lib.LibUserInfo;
import bean.lib.format.LibBorrowState;
import bean.lib.format.LoginFormat;
import bean.office.ElectiveCourseColumn;
import bean.office.ScoreColumn;

public class HtmlUtil {

	public static String getLibCodePicUrl(String html) throws LibException{
		String result = "";
		try{
			result = "http://210.38.162.2/OPAC/"+ Jsoup.parse(html).getElementById("ccodeimg").attr("src")+"?rd="+Math.random();
		}catch (Exception e) {
			throw new LibException(4, "连接服务器失败");
		}
		return result;
	}
	
	
	/**
	 * 判断账号是否验证成功 从图书馆主页面的html解析取得主要一些信息
	 * 
	 * @param result
	 * @param html
	 * @throws LibException 
	 */
	public static void parseLibLoginHtml(LoginFormat result,
			String html) throws LibException {
		Document doc = Jsoup.parse(html);
		Element userInfoContent = doc.getElementById("userInfoContent");
		if (userInfoContent == null) {
			// 接收的html不对
			if (doc.getElementById("ctl00_ContentPlaceHolder1_lblErr_Lib") != null) {
//				result.setError(doc
//						.getElementById("ctl00_ContentPlaceHolder1_lblErr_Lib")
//						.child(0).text());
				throw new LibException(2, doc
						.getElementById("ctl00_ContentPlaceHolder1_lblErr_Lib")
						.child(0).text());
			} else {
				throw new LibException(6, "未知错误");
			}
		} else {
			// //获取参数,记得设置cookie
			LibUserInfo userInfo = new LibUserInfo(userInfoContent.child(0)
					.child(1).text(), userInfoContent.child(1).child(1).text(),
					userInfoContent.child(2).child(1).text(), userInfoContent
							.child(3).child(1).text(), userInfoContent.child(4)
							.child(1).text(), userInfoContent.child(5).child(1)
							.text(), userInfoContent.child(6).child(1).text(),
					userInfoContent.child(7).child(1).text(), userInfoContent
							.child(8).child(1).text(), userInfoContent.child(9)
							.child(1).text());
			result.setUserinfo(userInfo);
		}
	}
	
	public static void FZBorrowStatePage(LibBorrowState item, String html) throws LibException {
		if("".equals(html)){
			throw new LibException(4, "无法连接学校服务器");
		}
		Document doc = Jsoup.parse(html);
		Element div = doc.getElementById("borrowedcontent");
		if(div == null){
			throw new LibException(1, "登录过期");
		}else{
			Element tbody = div.select("tbody").first();
			for(int i=0;i<tbody.children().size();i++){
				Element tr = tbody.child(i);
				BookBorrowedState column = new BookBorrowedState(tr.child(1).text()
						,  tr.child(2).child(0).text()
						,tr.child(3).text()
						, tr.child(4).text()
						, tr.child(5).text()
						,tr.child(6).text()
						,tr.child(2).child(0).attr("href")
//						,ConstantValue.LibHost + tr.child(2).child(0).attr("href")
						);
				item.getColumns().add(column);
			}
		}
	}


	/**
	 * 解析正方系统主页面
	 * @param content2String
	 * @throws LibException 
	 */
	public static boolean parseOfficeMainHtml(String html) throws LibException {
		if(html == null){
			throw new LibException(504, "获取的html为空");
		}else{
			Document doc = Jsoup.parse(html);
			if (doc.getElementById("xhxm") == null) {
//				throw new LibException(100, "账号或者密码错误！");
				return false;
			} else {
//				String tName = doc.getElementById("xhxm").text();
				return true;
			}
		}
	}

	public static List<ElectiveCourseColumn> parseElectiveHtml(String html) throws LibException, OutOfLoginException {
		if(html == null){
			throw new LibException(504, "获取的html为空");
		}else{
			Document doc = Jsoup.parse(html);
			Element tag = doc.getElementById("DBGrid");
			if (tag == null) {
				throw new OutOfLoginException();
			} else {
				List<ElectiveCourseColumn> list = new ArrayList<ElectiveCourseColumn>();
				Elements trs = doc.getElementById("DBGrid").select("tr");
				for (int i = 1; i < trs.size(); i++) {
					Element tr = trs.get(i);
					ElectiveCourseColumn column = new ElectiveCourseColumn(tr
							.child(0).text(), tr.child(1).text(), tr.child(2)
							.child(0).text(), tr.child(3).text(), tr.child(4)
							.text(), tr.child(5).child(0).text(), tr.child(6)
							.text(), tr.child(7).text(), tr.child(8).child(0)
							.text(), tr.child(9).text(), tr.child(10).text(), tr
							.child(11).text(), tr.child(12).text(), tr.child(13)
							.text(), tr.child(14).text());
					list.add(column);
				}
				return list;
			}
		
		}
	}

	/**
	 * 把获取成绩的html页面封装成bean
	 * @param html
	 * @return
	 * @throws LibException
	 * @throws OutOfLoginException
	 */
	public static List<ScoreColumn> parseScoreHtml(String html) throws LibException, OutOfLoginException {
		if(html == null){
			throw new LibException(504, "获取的html为空");
		}else{
			Document doc = Jsoup.parse(html);
			Element loginTag = doc.getElementById("Datagrid1");
			if (loginTag == null) {
				throw new OutOfLoginException();
			} else{
				List<ScoreColumn> list = new ArrayList<ScoreColumn>();
				Elements trs = doc.getElementById("Datagrid1").select("tr");
				if (trs != null) {
					for (int i = 1; i < trs.size(); i++) {
						Element element = trs.get(i);
						ScoreColumn column = new ScoreColumn(element.child(0)
								.text(), element.child(1).text(), element.child(2)
								.text(), element.child(3).text(), element.child(4)
								.text(), element.child(5).text(), element.child(6)
								.text(), element.child(7).text(), element.child(8)
								.text(), element.child(9).text(), element.child(10)
								.text(), element.child(11).text(), element
								.child(12).text(), element.child(13).text(),
								element.child(12).text());
						list.add(column);
					}
				}
				return list;
			}
		}
	}
}
