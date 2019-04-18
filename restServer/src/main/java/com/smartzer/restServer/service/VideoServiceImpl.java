package com.smartzer.restServer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.smartzer.restServer.model.Video;

@Service("videoService")
public class VideoServiceImpl implements VideoService {
	
	protected static Map<Long, Video> VIDEO_MAP = new HashMap<Long, Video>();

	@Override
	public List<Video> findAllVideos() {
		
		if(!VIDEO_MAP.isEmpty()){
			return new ArrayList<Video>(VIDEO_MAP.values());
		}
		return null;
	}

	@Override
	public Video findById(long id) {
		if(VIDEO_MAP.containsKey(id)){
			return VIDEO_MAP.get(id);
		}
		return null;
	}

	@Override
	public void deleteVideoById(long id) {
		if(VIDEO_MAP.containsKey(id)){
			VIDEO_MAP.remove(id);
		}
	}

	@Override
	public Video updateVideo(Video currentVideo, Video video) {
		
		if(currentVideo != null 
				&& currentVideo.getId() > 0
				&& video != null){
			currentVideo.setName(video.getName());
			currentVideo.setUrl(video.getUrl());
			VIDEO_MAP.put(currentVideo.getId(), currentVideo);
		}
		return currentVideo;
	}

	@Override
	public boolean doesVideoExist(Video video) {
		
		if(video != null && !VIDEO_MAP.isEmpty()){
			List<Video> videoList = new ArrayList<Video>(VIDEO_MAP.values());
			for(Video existingVideo : videoList){
				if(existingVideo.getName().equalsIgnoreCase(video.getName())){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Video saveVideo(Video video) {
		
		Video newVideo = null;
		if(video != null){
			newVideo = new Video();
			newVideo.setName(video.getName());
			newVideo.setUrl(video.getUrl());
			newVideo.setId(VIDEO_MAP.size() + 1);
			VIDEO_MAP.put(newVideo.getId(), newVideo);
		}
		return newVideo;
	}
	
	public void clearMap(){
		VIDEO_MAP = new HashMap<Long, Video>();
	}

}
