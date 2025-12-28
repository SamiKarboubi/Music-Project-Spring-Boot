package com.example.musicproject.dao.repositories;

import com.example.musicproject.dao.entities.Album;
import com.example.musicproject.dao.entities.Artist;
import com.example.musicproject.dao.entities.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song,Long> {
    public List<Song> findAllByArtist(Artist artist);
    public Page<Song> findByNameContains(String keyword, Pageable pageable);
}
