package com.cognixia.jump.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class DaoRunner {

	public static void main(String[] args) {
		
		TVTrackerDao tvDao = new TVTrackerDaoSql();

		try {
			tvDao.setConnection();
			
			HashMap<String, Integer> usersCompletedShows = tvDao.getNumUsersCompletedShow();
			
			System.out.println(usersCompletedShows);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
