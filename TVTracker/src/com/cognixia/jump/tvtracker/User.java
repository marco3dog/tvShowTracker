package com.cognixia.jump.tvtracker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User {
	
	private int id;
	private String name;
	private String password;
	private List<UserShow> list;
	private Connection conn;
	
	
	public User(int id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.list = new ArrayList<UserShow>();
		
		try{
			this.conn = BetterConnectionManager.getConnection();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		createList();
	}
	
	// Constructor for AdminUser
	public User() {
		super();
		
		try{
			this.conn = BetterConnectionManager.getConnection();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<UserShow> getList() {
		return list;
	}
	public void setList(List<UserShow> list) {
		this.list = list;
	}
	
	private void createList(){
		//List<UserShow> usersShows = new ArrayList<UserShow>(); 
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT s.showid, s.name, us.episodes, s.episodes FROM user_shows us JOIN user u ON us.userid = u.userid JOIN shows s ON us.showid = s.showid WHERE us.userid = " + this.id);
				) {
				while (rs.next()) {
					int id = rs.getInt("showid");
					String name = rs.getString("name");
					int episodesWatched = rs.getInt("us.episodes");
					int episodesTotal = rs.getInt("s.episodes");
					UserShow show = new UserShow(id, name, episodesTotal, episodesWatched);
					list.add(show);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void addShowToList(int showId, int episodesWatched) {
		
		try (Statement stmt = conn.createStatement()) {
			int updated = stmt.executeUpdate("INSERT INTO user_shows values(" + getId() + ", " + showId + ", " + episodesWatched + ")");
			
			if (updated != 0)
				System.out.println("Show successfully added to list.");
			
		} catch (SQLException e) {
			System.out.println("Show cannot be added to list. Try again.");
		}
	}
	
	public void updateShowInList(int showId, int episodesWatched) {
		
		// Get total amount of episodes the show has
		int totalEpisodes = 0;
		
		try (Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT episodes FROM shows WHERE showid = " + showId);
			) {
			while (rs.next()) {
				totalEpisodes = rs.getInt("episodes");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Checks if episodesWatched is greater than the amount of episodes the show has or less than 0
		if (episodesWatched > totalEpisodes || episodesWatched < 0) {
			System.out.println("Invalid amount of episodes watched. Please check your input and try again.");
			return;
		}
		// If the value of episodesWatched is valid, then continue on with the method
		try (Statement stmt = conn.createStatement();) {
			
			int updated = stmt.executeUpdate("UPDATE user_shows SET episodes = " + episodesWatched + " WHERE showid = " + showId + " AND userid = " + getId());
			
			if (updated != 0)
				System.out.println("List entry successfully updated.");
			
		} catch (SQLException e) {
			System.out.println("List cannot be updated. Try again.");
		}
	}
	
	public void removeShowFromList(int showId) {
		// Check if the show the user wants to remove is actually in their list
		HashSet<Integer> showIds = new HashSet<Integer>();
		for (UserShow show : list) {
			showIds.add(show.getShowId());
		}
		// If the show is not in their list, notify the user and stop the method
		if (!showIds.contains(showId)) {
			System.out.println("Show does not exist in your list. Please check your input and try again.");
			return;
		}
		
		// Remove the show from the user's list
		try (Statement stmt = conn.createStatement();) {
			int updated = stmt.executeUpdate("DELETE FROM user_shows WHERE showid = " + showId);
			
			if (updated != 0)
				System.out.println("List entry successfully removed.");
			
		} catch (SQLException e) {
			System.out.println("List entry could not be removed. Try again.");
		}
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", list=" + list + "]";
	}
	
	

}
