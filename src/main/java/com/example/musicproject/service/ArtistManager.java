package com.example.musicproject.service;

import com.example.musicproject.dao.entities.Album;
import com.example.musicproject.dao.entities.Artist;
import com.example.musicproject.dao.entities.Song;
import com.example.musicproject.dao.entities.User;
import com.example.musicproject.dao.repositories.AlbumRepository;
import com.example.musicproject.dao.repositories.ArtistRepository;
import com.example.musicproject.dao.repositories.SongRepository;
import com.example.musicproject.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistManager implements ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Artist findArtistById(Long id) {
        if (id == null) return null;
        return artistRepository.findById(id).orElse(null);
    }

    @Override
    public Artist findArtistByUsername(String username) {
        if (username == null) return null;
        return artistRepository.findByUsername(username);
    }

    @Override
    public boolean deleteArtistById(Long id) {
        if (id != null && artistRepository.existsById(id)) {
            artistRepository.deleteById(id);
            return true;
        }
        return false;
    }



    @Override
    public Artist updateArtist(Artist artist) {
        if (artist == null || artist.getId() == null) return null;
        if (artistRepository.existsById(artist.getId())) {
            return artistRepository.save(artist);
        }
        return null;
    }

    @Override
    public Page<Artist> findAllArtists(int page, int size, String keyword) {
        if (keyword.isEmpty()) {
            return artistRepository.findAll(PageRequest.of(page, size));
        }
        Page<Artist> result = artistRepository.findByUsernameContains(keyword, PageRequest.of(page, size));
        if (result.isEmpty()) {
            return Page.empty(PageRequest.of(page, size));
        }
        return result;
    }

    @Override
    public Artist addArtist(Artist artist) {
        if (artist == null) return null;
        if (artistRepository.findByUsername(artist.getUsername()) != null) {
            return null;
        }
        return artistRepository.save(artist);
    }

    @Override
    public Artist findArtistByAlbum(Album album) {
        if (album == null || album.getId() == null) return null;

        Album a = albumRepository.findById(album.getId()).orElse(null);
        if (a != null) {
            return a.getArtist();
        }
        return null;
    }

    @Override
    public Artist findArtistBySong(Song song) {
        if (song == null || song.getId() == null) return null;

        Song s = songRepository.findById(song.getId()).orElse(null);
        if (s != null) {
            return s.getArtist();
        }
        return null;
    }

    @Override
    public List<Artist> findFollowingsByUserId(Long userId) {
        if (userId == null) return null;
        if (userRepository.existsById(userId)) {
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                return new ArrayList<>(user.getFollowings());
            }
        }
        return null;
    }

    @Override
    public boolean removeFollower(Long artistId,Long userId){
        if (userId != null && artistId != null &&
                userRepository.existsById(userId) &&
                artistRepository.existsById(artistId)) {

            User user = userRepository.findById(userId).get();
            Artist artist = artistRepository.findById(artistId).get();

            if (artist.getFollowers().contains(user)) {
                artist.getFollowers().remove(user);
                artistRepository.save(artist);
                return true;
            }
        }
        return false;
    }
}
