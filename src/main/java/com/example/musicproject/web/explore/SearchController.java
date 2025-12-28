package com.example.musicproject.web.explore;


import com.example.musicproject.dao.entities.Album;
import com.example.musicproject.dao.entities.Artist;
import com.example.musicproject.dao.entities.Song;
import com.example.musicproject.service.AlbumService;
import com.example.musicproject.service.ArtistService;
import com.example.musicproject.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/explore")
public class SearchController {

    @Autowired
    private ArtistService artistService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private SongService songService;


    @GetMapping("/results")
    public String search(Model model,
                         @RequestParam(defaultValue = "") String keyword,
                         @RequestParam(defaultValue = "") String type,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "8") int taille) {


        if (keyword == null || keyword.trim().isEmpty()) {
            model.addAttribute("artists", new java.util.ArrayList<>());
            model.addAttribute("songs", new java.util.ArrayList<>());
            model.addAttribute("albums", new java.util.ArrayList<>());
            model.addAttribute("keyword", "");
            model.addAttribute("type", type != null ? type : "all");
            return "explore/results";
        }


        if (type.isEmpty() || type.equals("all")) {

            Page<Artist> artists = artistService.findAllArtists(page, taille, keyword);
            Page<Song> songs = songService.findAllSongs(page, taille, keyword);
            Page<Album> albums = albumService.findAllAlbums(page, taille, keyword);

            model.addAttribute("artists", artists.getContent());
            model.addAttribute("songs", songs.getContent());
            model.addAttribute("albums", albums.getContent());

            model.addAttribute("artistPages", new int[artists.getTotalPages()]);
            model.addAttribute("songPages", new int[songs.getTotalPages()]);
            model.addAttribute("Pages", new int[albums.getTotalPages()]);
            model.addAttribute("currentPage", page);
            model.addAttribute("keyword", keyword);
            model.addAttribute("type", "all");

        } else {

            switch (type) {
                case "artist" -> {
                    Page<Artist> artists = artistService.findAllArtists(page, taille, keyword);
                    model.addAttribute("artists", artists.getContent());
                    model.addAttribute("pages", new int[artists.getTotalPages()]);
                }

                case "album" -> {
                    Page<Album> albums = albumService.findAllAlbums(page, taille, keyword);
                    model.addAttribute("albums", albums.getContent());
                    model.addAttribute("pages", new int[albums.getTotalPages()]);
                }

                case "song" -> {
                    Page<Song> songs = songService.findAllSongs(page, taille, keyword);
                    model.addAttribute("songs", songs.getContent());
                    model.addAttribute("pages", new int[songs.getTotalPages()]);
                }
            }

            model.addAttribute("currentPage", page);
            model.addAttribute("keyword", keyword);
            model.addAttribute("type", type);
        }

        return "explore/results";
    }
}
