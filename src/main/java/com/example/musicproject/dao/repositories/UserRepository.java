package com.example.musicproject.dao.repositories;

import com.example.musicproject.dao.entities.Playlist;
import com.example.musicproject.dao.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findByUsername(String username);
    public Page<User> findByUsernameContains(String keyword, Pageable pageable);
}
