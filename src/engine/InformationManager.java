package engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.ConnectionUtil;

import bean.News;


public class InformationManager {

	public static ArrayList<News> selectAllNews(int page) {
		ArrayList<News> news = new ArrayList<News>();
		try {
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("Select * from news where type!=1 order by id DESC limit ?,10");
			ps.setInt(1, page);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				News newss = new News(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(6), rs.getString(7));
				news.add(newss);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return news;
	}
	
	public static ArrayList<News> selectTopicNews() {
		ArrayList<News> news = new ArrayList<News>();
		try {
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("select * from news where type=1 order by id DESC");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				News newss = new News(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(6), rs.getString(7));
				news.add(newss);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return news;
	}
}
