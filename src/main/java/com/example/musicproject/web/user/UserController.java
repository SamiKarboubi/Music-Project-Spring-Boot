package com.example.musicproject.web.user;


import com.example.musicproject.dao.entities.Artist;
import com.example.musicproject.dao.entities.Playlist;
import com.example.musicproject.dao.entities.Song;
import com.example.musicproject.dao.entities.User;
import com.example.musicproject.service.ArtistService;
import com.example.musicproject.service.PlaylistService;
import com.example.musicproject.service.SongService;
import com.example.musicproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private SongService songService;
    @Autowired
    private ArtistService artistService;

    @GetMapping("/home")
    public String home(Model model, Authentication auth) {
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        if (user != null) {
            model.addAttribute("playlists", playlistService.findPlaylistsByCreatorId(user.getId()));
        }
        return "user/home";
    }

    @GetMapping("/likedSongs")
    public String likedSongs(Model model, Authentication auth) {
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        if (user != null) {
            List<Song> likedSongs = songService.findLikedSongsByUserId(user.getId());
            model.addAttribute("likedSongs", likedSongs != null ? likedSongs : new java.util.ArrayList<>());
            model.addAttribute("user", user);
        }
        return "user/likedSongs";
    }


    @GetMapping("/playlist")
    public String playlists(Model model, Authentication auth) {
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        if (user != null) {
            List<Playlist> playlists = playlistService.findPlaylistsByCreatorId(user.getId());
            model.addAttribute("playlists", playlists != null ? playlists : new java.util.ArrayList<>());
            model.addAttribute("user", user);
        }
        return "user/playlists";
    }

    @GetMapping("/playlist/{id}")
    public String playlistInfo(Model model, Authentication auth, @PathVariable long id) {
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        Playlist playlist = playlistService.findPlaylistById(id);
        if (playlist != null && user != null) {
            // Vérifier que la playlist appartient à l'utilisateur
            if (playlist.getCreator() != null && playlist.getCreator().getId().equals(user.getId())) {
                model.addAttribute("playlist", playlist);
                model.addAttribute("user", user);
            } else {
                return "redirect:/user/playlist";
            }
        } else {
            return "redirect:/user/playlist";
        }
        return "user/playlist_info";
    }

    @GetMapping("/likedSongs/{id}")
    public String likeSong(Model model, Authentication auth, @PathVariable long id) {
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        Song song = songService.findSongById(id);
        if (user != null && song != null) {
            userService.addLikedSong(user.getId(), song.getId());
        }
        return "redirect:/user/likedSongs";
    }

    @GetMapping("/followings")
    public String showFollowings(Model model, Authentication auth) {
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        if (user != null) {
            List<Artist> followings = artistService.findFollowingsByUserId(user.getId());
            model.addAttribute("followings", followings != null ? followings : new java.util.ArrayList<>());
            model.addAttribute("user", user);
        }
        return "user/followings";
    }

    @GetMapping("/addfollowings/{id}")
    public String followArtist(Model model, Authentication auth, @PathVariable long id) {
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        Artist artist = artistService.findArtistById(id);
        if (user != null && artist != null) {
            userService.addFollowing(user.getId(), artist.getId());
        }
        return "redirect:/user/followings";
    }

    @GetMapping("/deletefollowings/{id}")
    public String unfollowArtist(Model model, Authentication auth, @PathVariable long id) {
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        Artist artist = artistService.findArtistById(id);
        if (user != null && artist != null) {
            userService.removeFollowing(user.getId(), artist.getId());
        }
        return "redirect:/user/followings";
    }

    @GetMapping("/playlist/addSong/{idp}/{ids}")
    public String addSongPlaylist(Model model, Authentication auth, @PathVariable long idp, @PathVariable long ids) {
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        Playlist playlist = playlistService.findPlaylistById(idp);
        if (user != null && playlist != null && playlist.getCreator() != null && 
            playlist.getCreator().getId().equals(user.getId())) {
            playlistService.addSongToPlaylist(idp, ids);
        }
        return "redirect:/user/playlist/"+idp;
    }

    @GetMapping("/playlist/deleteSong/{idp}/{ids}")
    public String removeSongPlaylist(Model model, Authentication auth, @PathVariable long idp, @PathVariable long ids) {
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        Playlist playlist = playlistService.findPlaylistById(idp);
        if (user != null && playlist != null && playlist.getCreator() != null && 
            playlist.getCreator().getId().equals(user.getId())) {
            playlistService.removeSongFromPlaylist(idp, ids);
        }
        return "redirect:/user/playlist/"+idp;
    }

    @GetMapping("/playlist/add")
    public String showAddPlaylistForm(Model model,Authentication auth) {
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("playlist",new Playlist());
        return "user/playlist/add";
    }

    @PostMapping("/playlist/add")
    public String addPlaylist(@ModelAttribute Playlist playlist,
                                Model model,
                              Authentication auth) {
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        playlist.setCreator(user);
        Playlist savedPlaylist = playlistService.addPlaylist(playlist);
        return "redirect:/user/playlist/"+savedPlaylist.getId();
    }

    @GetMapping("/playlist/delete/{id}")
    public String deletePlaylist(Model model, Authentication auth, @PathVariable long id) {
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        Playlist playlist = playlistService.findPlaylistById(id);
        if (user != null && playlist != null && playlist.getCreator() != null && 
            playlist.getCreator().getId().equals(user.getId())) {
            playlistService.deletePlaylistById(id);
        }
        return "redirect:/user/playlist";
    }

    @GetMapping("/playlist/edit/{id}")
    public String showEditPlaylistForm(Model model, Authentication auth, @PathVariable long id) {
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        Playlist playlist = playlistService.findPlaylistById(id);
        if (user != null && playlist != null && playlist.getCreator() != null && 
            playlist.getCreator().getId().equals(user.getId())) {
            model.addAttribute("playlist", playlist);
            model.addAttribute("user", user);
            return "user/playlist/edit";
        }
        return "redirect:/user/playlist";
    }

    @PostMapping("/playlist/edit")
    public String editPlaylist(Model model,Authentication auth,@ModelAttribute Playlist playlist){
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        playlistService.updatePlaylist(playlist);
        model.addAttribute("playlist", playlist);
        model.addAttribute("user", user);
        return "redirect:/user/playlist";
    }
}
