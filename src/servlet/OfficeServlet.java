package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.CommonUtil;

import engine.OfficeManager;

public class OfficeServlet extends HttpServlet {

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
		if ("getscore".equals(action)) {
			System.out.println("action=getscore");
			String tempUrl = request.getParameter("tempUrl");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String xm = request.getParameter("xm");
			outMap.putAll(OfficeManager.GetScore(tempUrl, username, xm,password));
		}else if("getelective".equals(action)){//获取选课成绩
			System.out.println("action=getelective");
			String tempUrl = request.getParameter("tempUrl");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String xm = request.getParameter("xm");
			outMap.putAll(OfficeManager.GetElective(tempUrl, username, xm,password));
		}
		CommonUtil.renderJson(response, outMap);
		outMap.clear();
	}

}
