package com.example.musicproject.web.admin;

import com.example.musicproject.dao.entities.Song;
import com.example.musicproject.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/song")
public class AdminSongController {

    @Autowired
    private SongService songService;

    @GetMapping
    public String listSongs(Model model,
                            @RequestParam(name="page", defaultValue = "0") int page,
                            @RequestParam(name="taille", defaultValue = "8") int taille,
                            @RequestParam(name="search", defaultValue = "") String keyword) {

        Page<Song> songs = songService.findAllSongs(page, taille, keyword);
        model.addAttribute("songs", songs.getContent());
        int[] pages = new int[songs.getTotalPages()];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i;
        }

        model.addAttribute("pages", pages);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "admin/song/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("song", new Song());
        return "admin/song/add";
    }

    @PostMapping("/add")
    public String addSong(@ModelAttribute("song") Song song, Model model) {
        songService.addSong(song);
        return "redirect:/admin/song";
    }

    @GetMapping("/delete/{id}")
    public String deleteSong(@PathVariable("id") Long id) {
        songService.deleteSongById(id);
        return "redirect:/admin/song";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Song song = songService.findSongById(id);
        model.addAttribute("song", song);
        return "admin/song/edit";
    }

    @PostMapping("/edit")
    public String editSong(@ModelAttribute("song") Song song) {
        songService.updateSong(song);
        return "redirect:/admin/song";
    }

    @GetMapping("/info/{id}")
    public String showSongInfo(Model model, @PathVariable("id") Long id) {
        Song song = songService.findSongById(id);
        model.addAttribute("song", song);
        return "admin/song/info";
    }
}
