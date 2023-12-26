package com.example.animerating.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/anime_rate")
public class MainController {

    @GetMapping("/")
    public String getMainPage(){
        return "main";
    }

    @GetMapping("/user_list")
    public String getUserListPage(){
        return "userList";
    }
}
