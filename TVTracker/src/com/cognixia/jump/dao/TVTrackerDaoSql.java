package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
				int showId = rs.getInt("showid");
				String name = rs.getString("name");
				int episodes = rs.getInt("episodes");
				
				Show show = new Show(showId, name, episodes);
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
		
		try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM show WHERE showid = ?");) {
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int showId = rs.getInt("showid");
				String name = rs.getString("name");
				int episodes = rs.getInt("episodes");
				
				Show show = new Show(showId, name, episodes);
				Optional<Show> foundShow = Optional.of(show);
				rs.close();
				return foundShow;
			} else {
				rs.close();
				return Optional.empty();
			}
		} catch (SQLException e) {
			return Optional.empty();
		}
	}

	@Override
	public void createShow(String showName, int episodes) {
		
		try (Statement stmt = conn.createStatement();) {
			int updated = stmt.executeUpdate("INSERT INTO shows values(null, " + "'" + showName + "'" + ", " + episodes + ")");
			
			if (updated != 0)
				System.out.println("Show successfully created.");
			else
				System.out.println("Show could not be created.");
		} catch (SQLException e) {
			System.out.println("Show could not be created.");
			e.printStackTrace();
		}
		
//		try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO shows values(null, name = ?, episodes = ?)");) {
//			pstmt.setString(1, showName);
//			pstmt.setInt(2, episodes);
//			
//			int updated = pstmt.executeUpdate();
//			if (updated > 0) {
//				System.out.println("Show successfully created");
//			}
//		} catch (SQLException e) {
//			System.out.println("Show could not be created.");
//		}
	}
	
	@Override
	public void deleteShow(int id) {
		
		try (Statement stmt = conn.createStatement();) {
			int updated = stmt.executeUpdate("DELETE FROM shows WHERE showid = " + id);
			
			if (updated != 0)
				System.out.println("Show has been successfully deleted.");
			else 
				System.out.println("Show could not be deleted.");
			
		} catch (SQLException e) {
			System.out.println("Show could not be deleted.");
		}
	}

	@Override
	public void updateShow(String showName, int episodes) {
		
		try (Statement stmt = conn.createStatement();) {
			int updated = stmt.executeUpdate("UPDATE shows SET episodes = " + episodes + " WHERE name = " + "'" + showName + "'");
			
			if (updated != 0)
				System.out.println("Show successfully updated.");
			else
				System.out.println("Show could not be updated.");
			
		} catch (SQLException e) {
			System.out.println("Show could not be updated.");
		}
	}
	
	

}
