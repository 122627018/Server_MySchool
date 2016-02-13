package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import engine.UserManager;


import util.CommonUtil;


public class UserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		Map<String, Object> outMap = new HashMap<String, Object>();
		if("login".equals(action)){
			System.out.println("登录");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
//			User u = UserManager.login(username,password);
//			if(u != null){
//				outMap.put("success", "1");
//				outMap.put("error", "");
//			}else{
//				outMap.put("success", "0");
//				outMap.put("error", "账号或者密码错误");
//			}
//			outMap.put("userInfo", u);
//			outMap.put("response", "login");
			outMap.putAll(UserManager.loginFromNet(username,password));
		}
		CommonUtil.renderJson(response, outMap);
		outMap.clear();
	}

}
