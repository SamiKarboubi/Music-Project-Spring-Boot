package com.example.musicproject.dao.repositories;

import com.example.musicproject.dao.entities.Album;
import com.example.musicproject.dao.entities.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album,Long> {
    public List<Album> findAllByArtist(Artist artist);

    Page<Album> findByNameContains(String keyword, Pageable pageable);
}