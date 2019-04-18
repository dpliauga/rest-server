package com.smartzer.restServer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VideoController {
	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listAllVideos() {
        return "index";
    }
    
}
