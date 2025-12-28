package com.example.musicproject.web.admin;

import com.example.musicproject.dao.entities.Playlist;
import com.example.musicproject.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/playlist")
public class AdminPlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @GetMapping
    public String listPlaylists(Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "taille", defaultValue = "8") int taille,
                                @RequestParam(name = "search", defaultValue = "") String keyword) {

        Page<Playlist> playlists = playlistService.findAllPlaylists(page, taille, keyword);
        model.addAttribute("playlists", playlists.getContent());
        int[] pages = new int[playlists.getTotalPages()];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i;
        }

        model.addAttribute("pages", pages);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "admin/playlist/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("playlist", new Playlist());
        return "admin/playlist/add";
    }

    @PostMapping("/add")
    public String addPlaylist(@ModelAttribute("playlist") Playlist playlist, Model model) {
        playlistService.addPlaylist(playlist);
        return "redirect:/admin/playlist";
    }

    @GetMapping("/delete/{id}")
    public String deletePlaylist(@PathVariable("id") Long id) {
        playlistService.deletePlaylistById(id);
        return "redirect:/admin/playlist";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Playlist playlist = playlistService.findPlaylistById(id);
        model.addAttribute("playlist", playlist);
        return "admin/playlist/edit";
    }

    @PostMapping("/edit")
    public String editPlaylist(@ModelAttribute("playlist") Playlist playlist) {
        playlistService.updatePlaylist(playlist);
        return "redirect:/admin/playlist";
    }

    @GetMapping("/info/{id}")
    public String showPlaylistInfo(Model model, @PathVariable("id") Long id) {
        Playlist playlist = playlistService.findPlaylistById(id);
        model.addAttribute("playlist", playlist);
        return "admin/playlist/info";
    }
}
