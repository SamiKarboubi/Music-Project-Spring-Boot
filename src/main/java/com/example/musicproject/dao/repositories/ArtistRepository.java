package com.example.musicproject.dao.repositories;

import com.example.musicproject.dao.entities.Album;
import com.example.musicproject.dao.entities.Artist;
import com.example.musicproject.dao.entities.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist,Long> {

    public List<Artist> findByAlbums(Album album);
    public List<Artist> findByAlbums(Song song);
    Page<Artist> findByUsernameContains(String keyword, Pageable pageable);
    Artist findByUsername(String username);
}
