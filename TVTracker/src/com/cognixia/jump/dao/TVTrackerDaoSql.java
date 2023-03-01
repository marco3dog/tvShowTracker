package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cognixia.jump.tvtracker.BetterConnectionManager;
import com.cognixia.jump.tvtracker.Show;

public class TVTrackerDaoSql implements TVTrackerDao {
	
	private Connection conn;

	@Override
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		conn = BetterConnectionManager.getConnection();
		
	}

	@Override
	public List<Show> getAllShows() {
		List<Show> showList = new ArrayList<Show>();
		
		try (Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM shows");
			) {
			while (rs.next()) {
				int id = rs.getInt("showid");
				String name = rs.getString("name");
				int episodes = rs.getInt("episodes");
				
				Show show = new Show(id, name, episodes);
				showList.add(show);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return showList;
	}

	@Override
	public Optional<Show> getShowById(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean createShow(Show show) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteShow(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateShow(Show show) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
