package com.cognixia.jump.tvtracker;

import java.util.List;

public class User {
	
	private int id;
	private String name;
	private String password;
	private List<Show> list;
	
	
	public User(int id, String name, String password, List<Show> list) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.list = list;
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
	public List<Show> getList() {
		return list;
	}
	public void setList(List<Show> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", list=" + list + "]";
	}
	
	

}