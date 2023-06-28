package com.fadavidos.demoSpringWeb.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    private final VideoService videoService;
    private final AppConfig appConfig;


    public HomeController(VideoService videoService, AppConfig appConfig) {
        this.videoService = videoService;
        this.appConfig = appConfig;
    }

    @GetMapping("/")
    public String index(@RequestBody Model model, Authentication authentication) {
        model.addAttribute("videos", videoService.getVideos());
        model.addAttribute("authentication", authentication);
        model.addAttribute("header", appConfig.header());
        model.addAttribute("intro", appConfig.intro());
        return "index";
    }

    @PostMapping("/new-video")
    public String newVideo(@ModelAttribute Video newVideo, Authentication authentication){
        videoService.create(newVideo, authentication.getName());
        return "redirect:/";
    }

    @PostMapping("/multi-field-search")
    public String multiFieldSearch(@ModelAttribute VideoSearch search, Model model, Authentication authentication) {
        List<VideoEntity> searchResults = videoService.search(search);
        model.addAttribute("videos", searchResults);
        model.addAttribute("authentication", authentication);
        return "index";
    }

    @PostMapping("/universal-search")
    public String universalSearch(@ModelAttribute UniversalSearch search, Model model, Authentication authentication){
        List<VideoEntity> videos = videoService.search(search);
        model.addAttribute("videos", videos);
        model.addAttribute("authentication", authentication);
        return "index";
    }

    @PostMapping("/delete/videos/{videoId}")
    public String deleteVideo(@PathVariable Long videoId) {
        videoService.delete(videoId);
        return "redirect:/";
    }
}
