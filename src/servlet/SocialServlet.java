package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.CommonUtil;

import bean.domain.CommentInfo;
import bean.domain.Social;

import engine.CommentManager;
import engine.SocialManager;


public class SocialServlet extends HttpServlet {

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
		if("getsoiallist".equals(action)){
			System.out.println("getsoiallist");
			//这里除了要得到sociallist之外，还要得到用户已经报名的socialid集合
//			List<Integer> userSocial = new ArrayList<Integer>();
			int page = Integer.valueOf(request.getParameter("page"));
			System.out.println("page="+page);
//			int userid = Integer.valueOf(request.getParameter("userid")==null?"-1":request.getParameter("userid"));
//			if(userid != -1 && page == 0){//说明用户处于登陆状态
//				userSocial.addAll(SocialManager.getUserSocial(userid));
//			}
//			int type = Integer.valueOf(request.getParameter("type"));
			List<Social> socials = SocialManager.getSocialList(1, page);
//			outMap.put("hasJoinIdList", userSocial);
			outMap.put("socialList", socials);
			outMap.put("success", 1);
			outMap.put("response","getsocial");
		}else if("getsocialcommentlist".equals(action)){
			int page = Integer.valueOf(request.getParameter("page"));
			int domainid = Integer.valueOf(request.getParameter("domainid"));
			int type = Integer.valueOf(request.getParameter("type"));
//			int type = 1;
			ArrayList<CommentInfo> list = CommentManager.getComment(page, domainid, type);
			outMap.put("commentList", list);
			outMap.put("success", 1);
			outMap.put("response","getsocialcommentlist");
		}
		CommonUtil.renderJson(response, outMap);
		outMap.clear();
	}

}
