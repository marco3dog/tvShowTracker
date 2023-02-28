package com.cognixia.jump.tvtracker;

public class Show {
	private int showId;
	private String name;
	private int episodes;
	private int watchedEpisodes;
	
	public Show() {}
	
	public Show(int showId, String name, int episodes, int watchedEpisodes) {
		this.showId = showId;
		this.name = name;
		this.episodes = episodes;
		this.watchedEpisodes = watchedEpisodes;
	}

	public int getShowId() {
		return showId;
	}
	public void setShowId(int showId) {
		this.showId = showId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEpisodes() {
		return episodes;
	}

	public void setEpisodes(int episodes) {
		this.episodes = episodes;
	}

	public int getWatchedEpisodes() {
		return watchedEpisodes;
	}

	public void setWatchedEpisodes(int watchedEpisodes) {
		this.watchedEpisodes = watchedEpisodes;
	}

	@Override
	public String toString() {
		return "Show [showId=" + showId + ", name=" + name + ", episodes=" + episodes + ", watchedEpisodes="
				+ watchedEpisodes + "]";
	}
	
}
