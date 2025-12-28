package com.example.musicproject.dao.repositories;

import com.example.musicproject.dao.entities.Album;
import com.example.musicproject.dao.entities.Playlist;
import com.example.musicproject.dao.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist,Long> {

    public List<Playlist> findAllByCreator(User user);
    public Playlist findByName(String name);
    Page<Playlist> findByNameContains(String keyword, Pageable pageable);
}
