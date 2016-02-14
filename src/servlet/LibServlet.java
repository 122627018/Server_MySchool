package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.CommonUtil;


import engine.LibManager;

public class LibServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		Map<String, Object> outMap = new HashMap<String, Object>();
		if("checkLib".equals(action)){
			//检查图书馆账号是否正确
			//正确就返回图书馆账号的信息，不正确就返回空
			System.out.println("checkLib");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String cookie = request.getParameter("cookie");
			String code = request.getParameter("code");
			Map<String, Object> login1 = LibManager.Login(username, password, cookie, code);
			outMap.putAll(login1);
			outMap.put("response", "checkLib");
		}else if("getpiccode".equals(action)){
			System.out.println("getpiccode");
			Map<String, Object> libCodePic1 = LibManager.getLibCodePic(false);
			outMap.putAll(libCodePic1);
		}else if("getborrowstate".equals(action)){
			System.out.println("getborrowstate");
			String cookie = request.getParameter("cookie");
			Map<String, Object> borrowState1 = LibManager.getBorrowState(cookie);
			outMap.putAll(borrowState1);
		}
		CommonUtil.renderJson(response, outMap);
	}

}
