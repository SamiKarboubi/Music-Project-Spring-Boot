package com.example.musicproject.service;

import com.example.musicproject.dao.entities.Album;
import com.example.musicproject.dao.entities.Artist;
import com.example.musicproject.dao.entities.Song;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AlbumService {
    Album findAlbumById(Long id);
    List<Album> findAlbumsByArtistId(Long artistId);
    boolean deleteAlbumById(Long id);
    Album updateAlbum(Album album);
    Page<Album> findAllAlbums(int page, int size, String keyword);
    Album addAlbum(Album album);
    Album findAlbumBySongId(Long songId);
    boolean addSongToAlbum(Long ida,Long ids);
    boolean removeSongFromAlbum(Long ida,Long ids);


}