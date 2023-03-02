package com.cognixia.jump.dao;

import java.io.IOException;
import java.sql.SQLException;

public class DaoRunner {

	public static void main(String[] args) {
		
		TVTrackerDao tvDao = new TVTrackerDaoSql();
		try {
			tvDao.setConnection();
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		tvDao.createShow("The Vampire Diaries", 52);
//		tvDao.deleteShow(18);
//		tvDao.updateShow("Breaking Bad", 70);

	}

}
