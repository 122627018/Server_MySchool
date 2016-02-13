package engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.ConnectionUtil;


import bean.domain.LostItem;

public class LostItemManager {

	public static List<LostItem> getLostItemList(int page) {
		ArrayList<LostItem> list = new ArrayList<LostItem>();
		try {
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("select * from lostitem where lost=? order by id DESC limit ?,10");
			ps.setInt(2, page);
			ps.setInt(1, 1);
			ResultSet rs = ps.executeQuery();
			LostItem item = null;
			while (rs.next()) {
				item = new LostItem(rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8),
						rs.getString(9), rs.getString(10), rs.getString(11),
						rs.getInt(12), rs.getInt(13), rs.getInt(14), 1);
				item.setUserInfo(UserManager.selectUserCommonInfo(item.getUserid()));
				list.add(item);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
