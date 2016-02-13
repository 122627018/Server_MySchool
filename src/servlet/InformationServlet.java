package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import bean.News;

import engine.InformationManager;

import util.CommonUtil;


public class InformationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		Map<String, Object> outMap = new HashMap<String, Object>();
		if("getall".equals(action)){
			System.out.println("getall");
			int page = Integer.valueOf(request.getParameter("page"));
			List<News> newss = InformationManager.selectAllNews(Integer.valueOf(page));
			List<News> topicNews = null;
			if(page==0){
				topicNews = InformationManager.selectTopicNews();
			}
			outMap.put("response", "newsgetall");
			outMap.put("normalNewsList", newss);
			outMap.put("topicNewsList", topicNews);
			CommonUtil.renderJson(response, outMap);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
