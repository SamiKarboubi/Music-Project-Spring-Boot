package com.example.musicproject.web.admin;

import com.example.musicproject.dao.entities.Album;
import com.example.musicproject.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/album")
public class AdminAlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping
    public String listAlbums(Model model,
                             @RequestParam(name = "page", defaultValue = "0") int page,
                             @RequestParam(name = "taille", defaultValue = "8") int taille,
                             @RequestParam(name = "search", defaultValue = "") String keyword) {

        Page<Album> albums = albumService.findAllAlbums(page, taille, keyword);
        model.addAttribute("albums", albums.getContent());
        int[] pages = new int[albums.getTotalPages()];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i;
        }

        model.addAttribute("pages", pages);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "admin/album/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("album", new Album());
        return "admin/album/add";
    }

    @PostMapping("/add")
    public String addAlbum(@ModelAttribute("album") Album album, Model model) {
        albumService.addAlbum(album);
        return "redirect:/admin/album";
    }

    @GetMapping("/delete/{id}")
    public String deleteAlbum(@PathVariable("id") Long id) {
        albumService.deleteAlbumById(id);
        return "redirect:/admin/album";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Album album = albumService.findAlbumById(id);
        model.addAttribute("album", album);
        return "admin/album/edit";
    }

    @PostMapping("/edit")
    public String editAlbum(@ModelAttribute("album") Album album) {
        albumService.updateAlbum(album);
        return "redirect:/admin/album";
    }

    @GetMapping("/info/{id}")
    public String showAlbumInfo(Model model, @PathVariable("id") Long id) {
        Album album = albumService.findAlbumById(id);
        model.addAttribute("album", album);
        return "admin/album/info";
    }
}
