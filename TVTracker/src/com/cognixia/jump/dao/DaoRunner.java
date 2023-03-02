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
			
			for(HashMap.Entry<String, Integer> set : usersCompletedShows.entrySet()) {
				System.out.println(set.getValue() + " user(s) have completed the show " + set.getKey());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
