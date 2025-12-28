package com.example.musicproject.service;

import com.example.musicproject.dao.entities.*;
import com.example.musicproject.dao.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongManager implements SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Song findSongById(Long id) {
        if (id == null) return null;
        return songRepository.findById(id).orElse(null);
    }

    @Override
    public List<Song> findSongsByArtistId(Long artistId) {
        if (artistId == null || !artistRepository.existsById(artistId)) return null;
        Artist artist = artistRepository.findById(artistId).orElse(null);
        return artist != null ? new ArrayList<>(artist.getSongs()) : null;
    }

    @Override
    public List<Song> findSongsByAlbumId(Long albumId) {
        if (albumId == null || !albumRepository.existsById(albumId)) return null;
        Album album = albumRepository.findById(albumId).orElse(null);
        return album != null ? new ArrayList<>(album.getSongs()) : null;
    }

    @Override
    public List<Song> findSongsByPlaylistId(Long playlistId) {
        if (playlistId == null || !playlistRepository.existsById(playlistId)) return null;
        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        return playlist != null ? new ArrayList<>(playlist.getSongs()) : null;
    }

    @Override
    public List<Song> findLikedSongsByUserId(Long userId) {
        if (userId == null || !userRepository.existsById(userId)) return null;
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? new ArrayList<>(user.getLikedSongs()) : null;
    }

    @Override
    public boolean deleteSongById(Long id) {
        if (id != null && songRepository.existsById(id)) {
            songRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Song updateSong(Song song) {
        if (song == null || song.getId() == null) return null;
        if (songRepository.existsById(song.getId())) {
            return songRepository.save(song);
        }
        return null;
    }

    @Override
    public Page<Song> findAllSongs(int page, int size, String keyword) {
       if(keyword.isEmpty()){
           return songRepository.findAll(PageRequest.of(page, size));
       }
       Page<Song> result = songRepository.findByNameContains(keyword,PageRequest.of(page,size));
       if (result.isEmpty()){
           return Page.empty(PageRequest.of(page, size));
       }
       return result;
    }

    @Override
    public Song addSong(Song song) {
        if (song == null || song.getArtist() == null) return null;
        return songRepository.save(song);
    }
}
