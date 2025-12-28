package com.example.musicproject.web.admin;

import com.example.musicproject.dao.entities.Artist;
import com.example.musicproject.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/artist")
public class AdminArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping
    public String listArtists(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "taille", defaultValue = "8") int taille,
                              @RequestParam(name = "search", defaultValue = "") String keyword) {

        Page<Artist> artists = artistService.findAllArtists(page, taille, keyword);
        model.addAttribute("artists", artists.getContent());
        int[] pages = new int[artists.getTotalPages()];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i;
        }

        model.addAttribute("pages", pages);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "admin/artist/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("artist", new Artist());
        return "admin/artist/add";
    }

    @PostMapping("/add")
    public String addArtist(@ModelAttribute("artist") Artist artist, Model model) {
        artistService.addArtist(artist);
        return "redirect:/admin/artist";
    }

    @GetMapping("/delete/{id}")
    public String deleteArtist(@PathVariable("id") Long id) {
        artistService.deleteArtistById(id);
        return "redirect:/admin/artist";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Artist artist = artistService.findArtistById(id);
        model.addAttribute("artist", artist);
        return "admin/artist/edit";
    }

    @PostMapping("/edit")
    public String editArtist(@ModelAttribute("artist") Artist artist) {
        artistService.updateArtist(artist);
        return "redirect:/admin/artist";
    }

    @GetMapping("/info/{id}")
    public String showArtistInfo(Model model, @PathVariable("id") Long id) {
        Artist artist = artistService.findArtistById(id);
        model.addAttribute("artist", artist);
        return "admin/artist/info";
    }
}