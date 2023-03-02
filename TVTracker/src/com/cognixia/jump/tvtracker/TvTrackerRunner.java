package com.cognixia.jump.tvtracker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.cognixia.jump.exceptions.PasswordNotFoundException;
import com.cognixia.jump.exceptions.UserNotFoundException;
import com.cognixia.jump.exceptions.UsernameNotFoundException;

public class TvTrackerRunner {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = BetterConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Connection failed.");
		}
		Scanner scan = new Scanner(System.in);
		User currentUser = login(scan, conn);	
		if(currentUser.getList().size() > 0) {
			System.out.println("------------");
			System.out.println("Your Shows:");
			for(int i = 0; i < currentUser.getList().size(); i++) {
				System.out.println(currentUser.getList().get(i).getName() + ": " + currentUser.getList().get(i).getEpisodesWatched() + "/" + currentUser.getList().get(i).getEpisodes() + " episodes watched");
			}
		}
		else {
			System.out.println("You have no tracked shows.");
		}
		System.out.println("------------");
		
		
//		 Test if methods work
		currentUser.addShowToList(7, 10);
//		
		currentUser.updateShowInList(1, -10);
	}
	
	//Functions
	//will update these with db connection
	private static boolean isUser(String username, Connection conn) {
		try(PreparedStatement ps = conn.prepareStatement("select * from user where username = ?")){
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		

		return false;
	}
	
	private static boolean isCorrectPassword(String username, String password, Connection conn) {
		try(PreparedStatement ps = conn.prepareStatement("select * from user where username = ? and password = ?")){
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private static User login(Scanner scan, Connection conn) {
		System.out.println("Welcome to your TV Show Tracker");	
		boolean valid = false;
		int id = 0;
		String usernameEntered = "";
		String passwordEntered = "";
		do {
			try {
				System.out.println("Enter your username: ");
				usernameEntered = scan.nextLine();
				if(!isUser(usernameEntered, conn)) {
					throw new UsernameNotFoundException(""); //make a custom exception for when entry is not found in the db
				}
				valid = true;
				System.out.println("Welcome, " + usernameEntered + ".");
			}
			catch(UsernameNotFoundException e) {
				System.out.println("Entry not found in the database.");
				valid = false;
			}
			catch(Exception e) {
				e.printStackTrace();
				valid = false;
			}
		}
		while(!valid);
		do {
			try {
				System.out.println("Enter your password: ");
				passwordEntered = scan.nextLine();
				if(!isCorrectPassword(usernameEntered, passwordEntered, conn)) {
					throw new PasswordNotFoundException("");
				}
				valid = true;
				System.out.println("Loading your shows...");
			}
			catch(PasswordNotFoundException e) {
				System.out.println("Incorrect password for " + usernameEntered + ". Try Again.");
				valid = false;
			}
			catch(Exception e) {
				e.printStackTrace();
				valid = false;
			}
		}
		while(!valid);
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select userid from user where username = '" + usernameEntered  +"'");)
		{
			while (rs.next()) {
				id = rs.getInt("userid");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return new User(id, usernameEntered, passwordEntered);
		
	}

}