package com.example.musicproject.web.artist;


import com.example.musicproject.dao.entities.*;
import com.example.musicproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    private UserService userService;
    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private SongService songService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private AlbumService albumService;

    @GetMapping("/home")
    public String home(Model model, Authentication auth) {
        String username = auth.getName();
        Artist artist = artistService.findArtistByUsername(username);
        if (artist != null) {
            model.addAttribute("albums", albumService.findAlbumsByArtistId(artist.getId()));
            model.addAttribute("songs", songService.findSongsByArtistId(artist.getId()));
            model.addAttribute("artist", artist);
        }
        return "artist/home";
    }


    @GetMapping("/albums")
    public String albums(Model model, Authentication auth) {
        String username = auth.getName();
        Artist artist = artistService.findArtistByUsername(username);
        List<Album> albums = albumService.findAlbumsByArtistId(artist.getId());
        model.addAttribute("albums", albums);
        model.addAttribute("artist", artist);
        return "artist/album";
    }

    @GetMapping("/songs")
    public String songs(Model model, Authentication auth) {
        String username = auth.getName();
        Artist artist = artistService.findArtistByUsername(username);
        List<Song> songs = songService.findSongsByArtistId(artist.getId());
        model.addAttribute("songs", songs);
        model.addAttribute("artist", artist);
        return "artist/song";
    }



    @GetMapping("/album/{id}")
    public String albumInfo(Model model, Authentication auth, @PathVariable long id) {
        String username = auth.getName();
        Artist artist = artistService.findArtistByUsername(username);
        Album album = albumService.findAlbumById(id);
        model.addAttribute("album", album);
        model.addAttribute("artist", artist);
        return "artist/album_info";
    }



    @GetMapping("/followers")
    public String showFollowers(Model model, Authentication auth) {
        String username = auth.getName();
        Artist artist = artistService.findArtistByUsername(username);
        List<User> followers = userService.findFollowersByArtistId(artist.getId());
        model.addAttribute("followers", followers);
        model.addAttribute("artist", artist);
        return "artist/followers";
    }


    @GetMapping("/followers/delete/{id}")
    public String deleteFollower(Model model, Authentication auth, @PathVariable long id) {
        String username = auth.getName();
        Artist artist = artistService.findArtistByUsername(username);
        User user = userService.findUserById(id);
        artistService.removeFollower(artist.getId(), user.getId());
        return "redirect:/artist/followers";
    }

    @GetMapping("/album/addSong/{ida}/{ids}")
    public String addSongAlbum(Model model, Authentication auth, @PathVariable long ida, @PathVariable long ids) {
        albumService.addSongToAlbum(ida,ids);
        return "redirect:/artist/album/"+ida;
    }

    @GetMapping("/album/deleteSong/{ida}/{ids}")
    public String removeSongAlbum(Model model, Authentication auth, @PathVariable long ida, @PathVariable long ids) {
        albumService.removeSongFromAlbum(ida,ids);
        return "redirect:/artist/album/"+ida;
    }

    @GetMapping("/album/add")
    public String showAddAlbumForm(Model model,Authentication auth) {
        String username = auth.getName();
        Artist artist = artistService.findArtistByUsername(username);
        model.addAttribute("artist", artist);
        model.addAttribute("album",new Album());
        return "artist/album_add";
    }

    @PostMapping("/album/add")
    public String addAlbum(@ModelAttribute Album album,
                              Model model,
                              Authentication auth) {
        String username = auth.getName();
        Artist artist = artistService.findArtistByUsername(username);
        album.setArtist(artist);
        Album savedAlbum = albumService.addAlbum(album);
        return "redirect:/artist/album/"+savedAlbum.getId();
    }

    @GetMapping("/album/delete/{id}")
    public String deleteAlbum(Model model, Authentication auth, @PathVariable long id) {
        albumService.deleteAlbumById(id);
        return "redirect:/artist/albums";

    }

    @GetMapping("/song/add")
    public String showSongAlbumForm(Model model,Authentication auth) {
        String username = auth.getName();
        Artist artist = artistService.findArtistByUsername(username);
        model.addAttribute("artist", artist);
        model.addAttribute("song",new Song());
        return "artist/song_add";
    }

    @PostMapping("/song/add")
    public String addSong(@ModelAttribute Song song,
                           Model model,
                           Authentication auth) {
        String username = auth.getName();
        Artist artist = artistService.findArtistByUsername(username);
        song.setArtist(artist);
        Song savedSong = songService.addSong(song);
        return "redirect:/artist/songs";
    }

    @GetMapping("/song/delete/{id}")
    public String deleteSong(Model model, Authentication auth, @PathVariable long id) {
        songService.deleteSongById(id);
        return "redirect:/artist/songs";

    }

    @GetMapping("/song/edit/{id}")
    public String showEditSongForm(Model model, Authentication auth, @PathVariable long id) {
        String username = auth.getName();
        Artist artist = artistService.findArtistByUsername(username);
        Song song = songService.findSongById(id);
        model.addAttribute("song", song);
        model.addAttribute("artist", artist);
        return "artist/song_edit";
    }

    @PostMapping("/song/edit")
    public String editSong(Model model,Authentication auth,@ModelAttribute Song song){
        String username = auth.getName();
        Artist artist = artistService.findArtistByUsername(username);
        songService.updateSong(song);
        model.addAttribute("song", song);
        model.addAttribute("artist", artist);
        return "redirect:/artist/songs";
    }

    @GetMapping("/album/edit/{id}")
    public String showEditAlbumForm(Model model, Authentication auth, @PathVariable long id) {
        String username = auth.getName();
        Artist artist = artistService.findArtistByUsername(username);
        Album album = albumService.findAlbumById(id);
        model.addAttribute("album", album);
        model.addAttribute("artist", artist);
        return "artist/album_edit";
    }

    @PostMapping("/album/edit")
    public String editAlbum(Model model,Authentication auth,@ModelAttribute Album album){
        String username = auth.getName();
        Artist artist = artistService.findArtistByUsername(username);
        albumService.updateAlbum(album);
        model.addAttribute("album", album);
        model.addAttribute("artist", artist);
        return "redirect:/artist/albums";
    }
}
