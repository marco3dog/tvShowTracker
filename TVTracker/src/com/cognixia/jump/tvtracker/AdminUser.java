package com.cognixia.jump.tvtracker;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminUser extends User {
	
	private Connection conn;
	
	public AdminUser() {
		super();
		List<UserShow> emptyArrayList = new ArrayList<>();
		super.setList(emptyArrayList);
		
		try{
			this.conn = BetterConnectionManager.getConnection();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
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
	}


	public void deleteShow(int id) {
		
		try (Statement stmt = conn.createStatement();) {
			int updated = stmt.executeUpdate("DELETE FROM shows WHERE showid = " + id);
			
			if (updated != 0)
				System.out.println("Show has been successfully deleted.");
			else 
				System.out.println("Show could not be deleted.");
			
		} catch (SQLException e) {
			System.out.println("Show could not be deleted.");
			e.printStackTrace();
		}
	}
	
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
