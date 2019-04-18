package com.smartzer.restServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.smartzer.restServer.model.Video;
import com.smartzer.restServer.service.VideoServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RestServerApplicationTests {
	
	public VideoServiceImpl videoService = new VideoServiceImpl();
	
	
	@After
	public void clearMap(){
		videoService.clearMap();
	}
	@Test
	public void findAllVideosNullTest() {
		List<Video> videos = videoService.findAllVideos();
		assertNull(videos);
	}
	
	@Test
	public void findAllVideosNotNullTest() {
		
		Video video = new Video();
		video.setName("name");
		video.setUrl("url");
		videoService.saveVideo(video);
		
		List<Video> videos = videoService.findAllVideos();
		assertNotNull(videos);
		assertEquals(1, videos.size());
	}
	
	@Test
	public void saveVideoTest() {
		
		Video video = new Video();
		video.setName("name");
		video.setUrl("url");
		Video savedVideo = videoService.saveVideo(video);
		assertNotNull(savedVideo);
		assertEquals("name", savedVideo.getName());
		assertEquals("url", savedVideo.getUrl());
		
		List<Video> videos = videoService.findAllVideos();
		assertNotNull(videos);
	}
	
	@Test
	public void findByIdTest() {
		
		Video video = new Video();
		video.setName("name");
		video.setUrl("url");
		Video savedVideo = videoService.saveVideo(video);
		assertNotNull(savedVideo);
		assertEquals("name", savedVideo.getName());
		assertEquals("url", savedVideo.getUrl());
		
		Video existingVideo = videoService.findById(1);
		assertNotNull(existingVideo);
		assertEquals(1, savedVideo.getId());
	}
	
	@Test
	public void deleteVideoByIdTest() {
		
		Video video = new Video();
		video.setName("name");
		video.setUrl("url");
		Video savedVideo = videoService.saveVideo(video);
		assertNotNull(savedVideo);
		assertEquals("name", savedVideo.getName());
		assertEquals("url", savedVideo.getUrl());
		
		Video existingVideo = videoService.findById(1);
		assertNotNull(existingVideo);
		assertEquals(1, savedVideo.getId());
		
		videoService.deleteVideoById(1);
		existingVideo = videoService.findById(1);
		assertNull(existingVideo);
	}
	
	@Test
	public void doesVideoExistTest(){
		Video video = new Video();
		video.setName("name");
		video.setUrl("url");
		boolean exists = videoService.doesVideoExist(video);
		assertFalse(exists);
		
		Video savedVideo = videoService.saveVideo(video);
		assertNotNull(savedVideo);
		assertEquals("name", savedVideo.getName());
		assertEquals("url", savedVideo.getUrl());
		
		exists = videoService.doesVideoExist(video);
		assertTrue(exists);
	}
	
	@Test
	public void updateVideoTest(){
		Video video = new Video();
		video.setName("name");
		video.setUrl("url");
		Video savedVideo = videoService.saveVideo(video);
		assertNotNull(savedVideo);
		assertEquals("name", savedVideo.getName());
		assertEquals("url", savedVideo.getUrl());
		
		video = videoService.findById(1);
		assertEquals(savedVideo.getName(), video.getName());
		assertEquals(savedVideo.getUrl(), video.getUrl());
		
		Video updatedVideo = new Video();
		updatedVideo.setName("updated name");
		updatedVideo.setUrl("updatedUrl");
		
		video = videoService.updateVideo(savedVideo, updatedVideo);
		assertEquals(updatedVideo.getName(), video.getName());
		assertEquals(updatedVideo.getUrl(), video.getUrl());
		
		video = videoService.findById(1);
		assertEquals(updatedVideo.getName(), video.getName());
		assertEquals(updatedVideo.getUrl(), video.getUrl());
	}

}
