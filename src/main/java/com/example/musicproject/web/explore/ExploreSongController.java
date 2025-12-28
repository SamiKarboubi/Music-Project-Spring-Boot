package com.example.musicproject.web.explore;

import com.example.musicproject.dao.entities.Song;
import com.example.musicproject.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/explore/song")
public class ExploreSongController {
    @Autowired
    private SongService songService;


    @GetMapping("/{id}")
    public String showSong(@PathVariable Long id, Model model) {
        Song song = songService.findSongById(id);
        model.addAttribute("song", song);
        return "explore/song";
    }
}
