package com.smartzer.restServer.service;

import java.util.List;

import com.smartzer.restServer.model.Video;

public interface VideoService {

	/**
	 * finds all videos
	 * @return
	 */
	List<Video> findAllVideos();

	/**
	 * finds video by id
	 * @param id
	 * @return
	 */
	Video findById(long id);

	/**
	 * deletes video by id
	 * @param id
	 */
	void deleteVideoById(long id);

	/**
	 * updates an existing video
	 * @param currentVideo
	 * @param video
	 * @return
	 */
	Video updateVideo(Video currentVideo, Video video);

	/**
	 * checks if video exists
	 * @param video
	 * @return
	 */
	boolean doesVideoExist(Video video);

	/**
	 * creates a new video
	 * @param video
	 * @return
	 */
	Video saveVideo(Video video);

}
