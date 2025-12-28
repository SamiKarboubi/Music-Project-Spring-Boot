package com.example.musicproject.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/artist/dashboard")
public class ArtistDashboardController {

    @GetMapping
    public String dashboardArtist() {
        return "artist/dashboard";
    }
}
