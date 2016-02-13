package engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.CommentInfo;
import bean.CommentInfo.ReplyComment;

import util.ConnectionUtil;


public class CommentManager {

	public static ArrayList<CommentInfo> getComment(int page, int domainid,
			int type) {
		ArrayList<CommentInfo> comment = new ArrayList<CommentInfo>();
		try {
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("select * from comment where type=? and domainid=? order by id DESC");
			ps.setInt(1, type);
			ps.setInt(2, domainid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
//				CommentInfo commentinfo = new CommentInfo(rs.getInt(1),
//						rs.getInt(2), rs.getInt(3), rs.getString(4),
//						rs.getString(5), rs.getInt(6), rs.getInt(7),
//						rs.getInt(8),rs.getInt(9));
				CommentInfo commentinfo = new CommentInfo(rs.getInt(1), rs.getString(5), rs.getString(4),  rs.getInt(7), rs.getInt(6));
				commentinfo.setUserInfo(UserManager.selectUserCommonInfo(rs.getInt(3)));
				if(commentinfo.getReply() == 1){
					commentinfo.setReplyComment(getReplyComment(rs.getInt(8)));
				}
				comment.add(commentinfo);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comment;
	}
	
	public static ReplyComment getReplyComment(int tarGetId){
		ReplyComment result = null;
		try {
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("select delete,userid,content,floor from comment where id=?");
			ps.setInt(1, tarGetId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1) == 1){
					result = new ReplyComment(null, "此评论已经删除",0);
				}else{
					result = new ReplyComment(UserManager.selectUserCommonInfo(rs.getInt(2)), rs.getString(3),rs.getInt(4));
				}
				
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
