package com.cognixia.jump.tvtracker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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
		login(scan, conn);
	
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
	
	private static void login(Scanner scan, Connection conn) {
		System.out.println("Welcome to your TV Show Tracker");	
		boolean valid = false;
		String usernameEntered = "";
		String passwordEntered = "";
		do {
			try {
				System.out.println("Enter your username: ");
				usernameEntered = scan.nextLine();
				if(!isUser(usernameEntered, conn)) {
					throw new Exception(); //make a custom exception for when entry is not found in the db
				}
				valid = true;
				System.out.println("Welcome, " + usernameEntered + ".");
			}
			catch(Exception e) {
				System.out.println("Entry not found in the database.");
				valid = false;
			}
		}
		while(!valid);
		do {
			try {
				System.out.println("Enter your password: ");
				passwordEntered = scan.nextLine();
				if(!isCorrectPassword(usernameEntered, passwordEntered, conn)) {
					throw new Exception();
				}
				valid = true;
				System.out.println("Loading your shows...");
			}
			catch(Exception e) {
				System.out.println("Incorrect password for " + usernameEntered + ". Try Again.");
				valid = false;
			}
		}
		while(!valid);
	}

}