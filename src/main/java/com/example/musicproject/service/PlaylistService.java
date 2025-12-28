package com.example.musicproject.service;

import com.example.musicproject.dao.entities.Playlist;
import com.example.musicproject.dao.entities.Song;
import com.example.musicproject.dao.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PlaylistService {
    Playlist findPlaylistById(Long id);


    List<Playlist> findPlaylistsByCreatorId(Long creatorId);
    boolean deletePlaylistById(Long id);
    Playlist updatePlaylist(Playlist playlist);
    Page<Playlist> findAllPlaylists(int page, int taille, String keyword);
    Playlist addPlaylist(Playlist playlist);
    boolean addSongToPlaylist(Long playlistId, Long songId);
    boolean removeSongFromPlaylist(Long playlistId, Long songId);
}