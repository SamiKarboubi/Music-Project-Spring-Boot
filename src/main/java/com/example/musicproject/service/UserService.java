package com.example.musicproject.service;

import com.example.musicproject.dao.entities.Artist;
import com.example.musicproject.dao.entities.Playlist;
import com.example.musicproject.dao.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);
    User findUserById(Long id);
    User findUserByPlaylist(Playlist playlist);
    boolean deleteUserById(Long id);
    User updateUser(User user);
    Page<User> findAllUsers(int page, int taille, String keyword);
    User addUser(User user);
    boolean addLikedSong(Long userId, Long songId);
    boolean removeLikedSong(Long userId, Long songId);
    List<User> findFollowersByArtistId(Long artistId);
    boolean addFollowing(Long userId,Long artistId);
    boolean removeFollowing(Long userId,Long artistId);


}
