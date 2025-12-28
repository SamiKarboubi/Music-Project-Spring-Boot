package com.example.musicproject.service;

import com.example.musicproject.dao.entities.Album;
import com.example.musicproject.dao.entities.Artist;
import com.example.musicproject.dao.entities.Playlist;
import com.example.musicproject.dao.entities.Song;
import com.example.musicproject.dao.repositories.AlbumRepository;
import com.example.musicproject.dao.repositories.ArtistRepository;
import com.example.musicproject.dao.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumManager implements AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private SongRepository songRepository;

    @Override
    public Album findAlbumById(Long id) {
        if (id == null) return null;
        return albumRepository.findById(id).orElse(null);
    }

    @Override
    public List<Album> findAlbumsByArtistId(Long artistId) {
        if (artistId == null || !artistRepository.existsById(artistId)) return null;
        Artist artist = artistRepository.findById(artistId).orElse(null);
        return artist != null ? new ArrayList<>(artist.getAlbums()) : null;
    }

    @Override
    public boolean deleteAlbumById(Long id) {
        if (id != null && albumRepository.existsById(id)) {
            albumRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Album updateAlbum(Album album) {
        if (album == null || album.getId() == null) return null;
        if (albumRepository.existsById(album.getId())) {
            return albumRepository.save(album);
        }
        return null;
    }

    @Override
    public Page<Album> findAllAlbums(int page, int size, String keyword) {
        if (keyword.isEmpty()) {
            return albumRepository.findAll(PageRequest.of(page, size));
        }
        Page<Album> result = albumRepository.findByNameContains(keyword, PageRequest.of(page, size));
        if (result.isEmpty()) {
            return Page.empty(PageRequest.of(page, size));
        }
        return result;
    }

    @Override
    public Album addAlbum(Album album) {
        if (album == null || album.getArtist() == null) return null;
        return albumRepository.save(album);
    }

    @Override
    public Album findAlbumBySongId(Long songId) {
        if (songId == null || !songRepository.existsById(songId)) return null;
        Song song = songRepository.findById(songId).orElse(null);
        return song != null ? song.getAlbum() : null;
    }

    @Override
    public boolean addSongToAlbum(Long ida,Long ids){
        if (ida == null || ids == null) return false;
        if (!albumRepository.existsById(ida) || !songRepository.existsById(ids)) return false;

        Album album = albumRepository.findById(ida).orElse(null);
        Song song = songRepository.findById(ids).orElse(null);

        if (album == null || song == null) return false;

        if (!album.getSongs().contains(song)) {
            album.getSongs().add(song);
            albumRepository.save(album);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeSongFromAlbum(Long ida,Long ids){
        if (ida == null || ids == null) return false;
        if (!albumRepository.existsById(ida) || !songRepository.existsById(ids)) return false;

        Album album = albumRepository.findById(ida).orElse(null);
        Song song = songRepository.findById(ids).orElse(null);

        if (album == null || song == null) return false;

        if (album.getSongs().contains(song)) {
            album.getSongs().remove(song);
            albumRepository.save(album);
            return true;
        }
        return false;
    }
}
