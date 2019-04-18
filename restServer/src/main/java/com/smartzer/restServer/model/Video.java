package com.smartzer.restServer.model;

public class Video {
	
	public long id;	//id of the video
	public String name;	//name of the video
	public String url; //url of the video
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
