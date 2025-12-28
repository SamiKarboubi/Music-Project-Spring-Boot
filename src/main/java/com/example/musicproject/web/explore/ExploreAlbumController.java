package com.example.musicproject.web.explore;

import com.example.musicproject.dao.entities.Album;
import com.example.musicproject.dao.entities.Song;
import com.example.musicproject.service.AlbumService;
import com.example.musicproject.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/explore/album")
public class ExploreAlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private SongService songService;


    @GetMapping("/{id}")
    public String showAlbum(@PathVariable Long id, Model model) {
        Album album = albumService.findAlbumById(id);
        List<Song> songs = songService.findSongsByAlbumId(id);
        model.addAttribute("album", album);
        model.addAttribute("songs", songs);
        return "explore/album";
    }
}
