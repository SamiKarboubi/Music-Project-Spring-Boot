package com.example.musicproject.service;

import com.example.musicproject.dao.entities.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SongService {
    Song findSongById(Long id);
    List<Song> findSongsByArtistId(Long artistId);
    List<Song> findSongsByAlbumId(Long albumId);
    List<Song> findSongsByPlaylistId(Long playlistId);
    List<Song> findLikedSongsByUserId(Long userId);
    boolean deleteSongById(Long id);
    Song updateSong(Song song);
    Page<Song> findAllSongs(int page, int taille, String keyword);
    Song addSong(Song song);
}