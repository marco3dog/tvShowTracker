package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.cognixia.jump.tvtracker.Show;

public interface TVTrackerDao {

	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;

	public List<Show> getAllShows();
	
	public Optional<Show> getShowById(int id);
	
	public boolean createShow(Show show);
	
	public boolean deleteShow(int id);
	
	public boolean updateShow(Show show);
	
}
