package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import engine.LostItemManager;

import bean.LostItem;

import util.CommonUtil;



public class LostItemServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		Map<String, Object> outMap = new HashMap<String, Object>();
		if("getlostitemlist".equals(action)){
			System.out.println(action);
			int page = Integer.valueOf(request.getParameter("page"));
			List<LostItem> list = LostItemManager.getLostItemList(page);
//			System.out.println(list.get(0).getPtime());
			outMap.put("lostItemList", list);
			outMap.put("response", "getlostitemlist");
			outMap.put("success", 1);
			outMap.put("error", "");
		}
		CommonUtil.renderJson(response, outMap);
		outMap.clear();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
