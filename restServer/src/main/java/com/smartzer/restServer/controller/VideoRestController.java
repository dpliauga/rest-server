package com.smartzer.restServer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smartzer.restServer.model.Video;
import com.smartzer.restServer.service.VideoService;
import com.smartzer.restServer.util.CustomErrorType;

@RestController
public class VideoRestController {

	
	@Autowired
    public VideoService videoService; //Service which will do all data retrieval/manipulation work
 
    // -------------------Retrieve All Videos---------------------------------------------
 
    @RequestMapping(value = "/video", method = RequestMethod.GET)
    public ResponseEntity<List<Video>> listAllVideos() {
    	List<Video> videos = null;
    	try{
	        videos = videoService.findAllVideos();
	        if (videos == null || videos.isEmpty()) {
	            return new ResponseEntity(new CustomErrorType("No videos found"),HttpStatus.NOT_FOUND);
	        }
    	} catch (Exception ex){
    		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
        return new ResponseEntity<List<Video>>(videos, HttpStatus.OK);
    }
    
    // -------------------Retrieve Single Video------------------------------------------
    
    @RequestMapping(value = "/video/{id}", method = RequestMethod.GET)
    public ResponseEntity<Video> getVideo(@PathVariable("id") long id) {
    	Video video = null;
    	try{
	        video = videoService.findById(id);
	        if (video == null) {
	        	return new ResponseEntity(new CustomErrorType("Video with id : " + id + " not found"), HttpStatus.NOT_FOUND);
	        }
    	} catch (Exception ex){
    		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
        return new ResponseEntity<Video>(video, HttpStatus.OK);
    }
    
    // -------------------Create a Video-------------------------------------------
    
    @RequestMapping(value = "/video", method = RequestMethod.POST)
    public ResponseEntity<Video> createVideo(@RequestBody Video video) {
    	Video newVideo = null;
    	try{
	        if (videoService.doesVideoExist(video)) {
	        	return new ResponseEntity(new CustomErrorType("Video with : " + video.getName() + " name already exists"), HttpStatus.CONFLICT);
	        }
	        newVideo = videoService.saveVideo(video);
    	} catch (Exception ex){
    		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
 
        return new ResponseEntity<Video>(newVideo, HttpStatus.CREATED);
    }
    
    // ------------------- Update a Video ------------------------------------------------
    
    @RequestMapping(value = "/video/{id}", method = RequestMethod.POST)
    public ResponseEntity<Video> updateVideo(@PathVariable("id") long id, @RequestBody Video video) {
    	
    	Video currentVideo = null;
    	try{
	        currentVideo = videoService.findById(id);
	 
	        if (currentVideo == null) {
	        	return new ResponseEntity(new CustomErrorType("Video with id : " + id + " not found"), HttpStatus.NOT_FOUND);
	        }
	        currentVideo = videoService.updateVideo(currentVideo, video);
    	} catch (Exception ex){
    		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
        
        return new ResponseEntity<Video>(currentVideo, HttpStatus.OK);
    }
    
    // ------------------- Delete a Video-----------------------------------------
    
    @RequestMapping(value = "/video/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteVideo(@PathVariable("id") long id) {
 
    	try{
	        Video video = videoService.findById(id);
	        if (video == null) {
	        	return new ResponseEntity(new CustomErrorType("Video with id : " + id + " not found"), HttpStatus.NOT_FOUND);
	        }
	        videoService.deleteVideoById(id);
    	} catch (Exception ex){
    		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
