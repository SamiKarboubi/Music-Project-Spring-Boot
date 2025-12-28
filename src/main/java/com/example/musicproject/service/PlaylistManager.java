package com.example.musicproject.service;

import com.example.musicproject.dao.entities.Playlist;
import com.example.musicproject.dao.entities.Song;
import com.example.musicproject.dao.entities.User;
import com.example.musicproject.dao.repositories.PlaylistRepository;
import com.example.musicproject.dao.repositories.SongRepository;
import com.example.musicproject.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistManager implements PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SongRepository songRepository;

    @Override
    public Playlist findPlaylistById(Long id) {
        if (id == null) return null;
        return playlistRepository.findById(id).orElse(null);
    }

    @Override
    public List<Playlist> findPlaylistsByCreatorId(Long creatorId) {
        if (creatorId == null || !userRepository.existsById(creatorId)) return null;
        User user = userRepository.findById(creatorId).orElse(null);
        return user != null ? new ArrayList<>(user.getPlaylists()) : null;
    }

    @Override
    public boolean deletePlaylistById(Long id) {
        if (id != null && playlistRepository.existsById(id)) {
            playlistRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Playlist updatePlaylist(Playlist playlist) {
        if (playlist == null || playlist.getId() == null) return null;
        if (playlistRepository.existsById(playlist.getId())) {
            return playlistRepository.save(playlist);
        }
        return null;
    }

    @Override
    public Page<Playlist> findAllPlaylists(int page, int size, String keyword) {
        if (keyword.isEmpty()) {
            return playlistRepository.findAll(PageRequest.of(page, size));
        }
        Page<Playlist> result = playlistRepository.findByNameContains(keyword, PageRequest.of(page, size));
        if (result.isEmpty()) {
            return Page.empty(PageRequest.of(page, size));
        }
        return result;
    }

    @Override
    public Playlist addPlaylist(Playlist playlist) {
        if (playlist == null || playlist.getCreator() == null) return null;
        return playlistRepository.save(playlist);
    }

    @Override
    public boolean addSongToPlaylist(Long playlistId, Long songId) {
        if (playlistId == null || songId == null) return false;
        if (!playlistRepository.existsById(playlistId) || !songRepository.existsById(songId)) return false;

        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        Song song = songRepository.findById(songId).orElse(null);

        if (playlist == null || song == null) return false;

        if (!playlist.getSongs().contains(song)) {
            playlist.getSongs().add(song);
            playlistRepository.save(playlist);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeSongFromPlaylist(Long playlistId, Long songId) {
        if (playlistId == null || songId == null) return false;
        if (!playlistRepository.existsById(playlistId) || !songRepository.existsById(songId)) return false;

        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        Song song = songRepository.findById(songId).orElse(null);

        if (playlist == null || song == null) return false;

        if (playlist.getSongs().contains(song)) {
            playlist.getSongs().remove(song);
            playlistRepository.save(playlist);
            return true;
        }
        return false;
    }


}
