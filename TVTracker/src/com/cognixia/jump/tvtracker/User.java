package com.cognixia.jump.tvtracker;

import java.util.List;

package com.cognixia.jump.tvtracker;

import java.sql.Connection;
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
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", list=" + list + "]";
	}
	
	

}
