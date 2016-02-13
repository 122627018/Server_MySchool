package engine;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import util.HtmlUtil;
import util.MyHttpUtil;
import bean.domain.web.ScoreColumn;
import bean.format.OfficeLogin.OfficeBehave;
import bean.http.NetReceiverData;
import bean.http.NetSendData;
import exception.LibException;
import exception.OutOfLoginException;

public class OfficeManager {

	public void CheckLogin(String username,String password){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String host = "http://210.38.162.116/";
		HttpPost httppost;
		try {
			httppost = new HttpPost(host);
			httppost.addHeader("Referer", "http://jwc.jyu.edu.cn/jwxt/");
			CloseableHttpResponse response = httpclient.execute(httppost);
			String location = response.getFirstHeader("Location").getValue();
			HttpGet httpGet = new HttpGet(host+location);
			httpGet.addHeader("Referer", host+location);
			response = httpclient.execute(httpGet);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 获取成绩
	 * @param tempUrl
	 * @param username
	 * @param xm
	 * @param password
	 * @return
	 */
	public static Map<String,Object> GetScore(
			String tempUrl, String username,String xm, String password) {
		try {
//			System.out.println("进入getscore方法,tempUrl="+tempUrl);
			String xm1  = URLEncoder.encode(xm,"gb2312");
//			System.out.println("tempUrl="+tempUrl+"--username="+username+"--password="+password+"--xm="+xm1);
			Map<String, OfficeBehave> officeBehaves = UserManager.getOfficeBehaves();
			OfficeBehave scoreBehave = officeBehaves.get("getscore");
			String pars = scoreBehave.getPars();
			String url = scoreBehave.getUrl();
			String referer1 = scoreBehave.getReferer();
//			System.out.println("referer="+referer1);
			url = url.replaceAll("#tempUrl#", tempUrl).replaceAll("#name#", xm1).replaceAll("#username#", username);
//			pars = pars.replaceAll("##", username)
			referer1 = referer1.replaceAll("#tempUrl#", tempUrl).replaceAll("#name#", xm1).replaceAll("#username#", username);
//			System.out.println("url="+url+"\n"+"referer="+referer1);
			
			NetSendData firstData = new NetSendData();
			firstData.setUrl(url);
			firstData.setHost("http://210.38.162.117/");
			firstData.getHeaders().put("Referer", referer1);
			firstData.setPars(pars);
			NetReceiverData sendPost = MyHttpUtil.sendPost(firstData);
			try {
				List<ScoreColumn> result = HtmlUtil.parseScoreHtml(sendPost.getContent2String());
				return UserManager.getResponseMap(200, "", tempUrl, result);
			} catch (LibException e) {
				return UserManager.getResponseMap(e.getState(), e.getMessage(), tempUrl, null);
			} catch (OutOfLoginException e) {
				//登录会话过期
				try {
					String turl = UserManager.RenewLogin(username, password);
					return GetScore(turl, username,xm, password);
				} catch (LibException e1) {
					return UserManager.getResponseMap(e1.getState(), e1.getMessage(), tempUrl, null);
				}
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("编码出错");
			e.printStackTrace();
		}
		return null;
	}
}
