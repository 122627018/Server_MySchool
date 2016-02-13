package engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.ConnectionUtil;

import bean.Social;


public class SocialManager {

	/**
	 * 获取活动列表
	 * 
	 * @param type
	 * @param page
	 * @return
	 */
	public static List<Social> getSocialList(int type, int page) {
		ArrayList<Social> socials = new ArrayList<Social>();
		try {
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("select * from social order by id DESC limit ?,3");
			ps.setInt(1, page);
			ResultSet rs = ps.executeQuery();
			Social social = null;
			while (rs.next()) {
				social = new Social(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7));
				social.setUserInfo(UserManager.selectUserCommonInfo(social.getUserid()));
				socials.add(social);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return socials;
	}
}
