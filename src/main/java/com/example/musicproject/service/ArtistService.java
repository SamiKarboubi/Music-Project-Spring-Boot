
package com.example.musicproject.service;

import com.example.musicproject.dao.entities.Album;
import com.example.musicproject.dao.entities.Artist;
import com.example.musicproject.dao.entities.Playlist;
import com.example.musicproject.dao.entities.Song;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ArtistService {
    Artist findArtistById(Long id);
    Artist findArtistByUsername(String username);
    boolean deleteArtistById(Long id);

    Artist updateArtist(Artist artist);
    Page<Artist> findAllArtists(int page, int size, String keyword);
    Artist addArtist(Artist artist);
    Artist findArtistByAlbum(Album album);
    Artist findArtistBySong(Song song);
    List<Artist> findFollowingsByUserId(Long userId);
    boolean removeFollower(Long artistId,Long userId);
}