package com.example.musicproject.web.explore;


import com.example.musicproject.dao.entities.Album;
import com.example.musicproject.dao.entities.Artist;
import com.example.musicproject.dao.entities.Song;
import com.example.musicproject.service.AlbumService;
import com.example.musicproject.service.ArtistService;
import com.example.musicproject.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/explore/artist")
public class ExploreArtistController {
    @Autowired
    private ArtistService artistService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private SongService songService;


    @GetMapping("/{id}")
    public String showArtist(@PathVariable Long id, Model model) {
        Artist artist = artistService.findArtistById(id);
        List<Album> albums = albumService.findAlbumsByArtistId(id);
        List<Song> songs = songService.findSongsByArtistId(id);
        model.addAttribute("artist", artist);
        model.addAttribute("albums",albums);
        model.addAttribute("songs",songs);
        return "explore/artist";
    }
}
