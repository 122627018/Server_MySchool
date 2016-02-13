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
		System.out.println("action="+action);
		Map<String, Object> outMap = new HashMap<String, Object>();
		if("checkLib".equals(action)){
			//检查图书馆账号是否正确
			//正确就返回图书馆账号的信息，不正确就返回空
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String cookie = request.getParameter("cookie");
			String code = request.getParameter("code");
//			ResponseData<LoginResponse> result = LibManager.Login(username,password,cookie,code);
//			if(result.getState() == 200){
//				outMap.put("behaves", result.getObj().getBehaves());
//				outMap.put("cookie", result.getObj().getCookie());
//				outMap.put("userinfo", result.getObj().getUserinfo());
//				outMap.put("error", "");
//			}else{
//				outMap.put("cookie", cookie);
//				outMap.put("state", result.getState());
//				outMap.put("error", result.getError());
//			}
			Map<String, Object> login1 = LibManager.Login1(username, password, cookie, code);
			System.out.println("login1.size()="+login1.size());
			outMap.putAll(login1);
			outMap.put("response", "checkLib");
		}else if("getpiccode".equals(action)){
			System.out.println("getpiccode");
			String cookie = request.getParameter("cookie");
//			ResponseData<LibLoginPage> libCodePic = LibManager.getLibCodePic(cookie);
//			outMap.put("cookie", libCodePic.getObj().getCookie());
//			outMap.put("picByte", libCodePic.getObj().getPicByte());
//			outMap.put("error", libCodePic.getError()+"");
//			outMap.put("state", 200);
			Map<String, Object> libCodePic1 = LibManager.getLibCodePic1(cookie,false);
			outMap.putAll(libCodePic1);
		}else if("getborrowstate".equals(action)){
			System.out.println("getborrowstate");
			String cookie = request.getParameter("cookie");
			String url = request.getParameter("url");
			String pars = request.getParameter("pars");
//			ResponseData<LibBorrowState> borrowState = LibManager.getBorrowState(url,cookie,pars);
//			if(borrowState.getState() == 200){
//				outMap.put("state", 200);
//				outMap.put("error", "");
//				outMap.put("columns", borrowState.getObj().getColumns());
//				outMap.put("cookie", borrowState.getObj().getCookie());
//				outMap.put("codeByte", null);
//				outMap.put("response", "");
//			}
			Map<String, Object> borrowState1 = LibManager.getBorrowState1(url, cookie, pars);
			outMap.putAll(borrowState1);
		}
		CommonUtil.renderJson(response, outMap);
	}

}
