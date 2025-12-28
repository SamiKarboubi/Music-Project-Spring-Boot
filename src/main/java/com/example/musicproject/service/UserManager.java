package com.example.musicproject.service;

import com.example.musicproject.dao.entities.Artist;
import com.example.musicproject.dao.entities.Playlist;
import com.example.musicproject.dao.entities.Song;
import com.example.musicproject.dao.entities.User;
import com.example.musicproject.dao.repositories.ArtistRepository;
import com.example.musicproject.dao.repositories.PlaylistRepository;
import com.example.musicproject.dao.repositories.SongRepository;
import com.example.musicproject.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserManager implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;

    public User findUserByUsername(String username){
        if (username != null) {
            return userRepository.findByUsername(username);
        }
        return null;
    }

    public User findUserById(Long id){
        return id != null ? userRepository.findById(id).orElse(null) : null;
    }

    public User findUserByPlaylist(Playlist playlist) {
        if (playlist == null || playlist.getId() == null) {
            return null;
        }
        Playlist p = playlistRepository.findById(playlist.getId()).orElse(null);
        if (p != null) {
            return p.getCreator();
        }
        return null;
    }

    public boolean deleteUserById(Long id){
        if (id != null && userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public User updateUser(User user){
        if (user != null && userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        }
        return null;
    }

    public Page<User> findAllUsers(int page, int taille,String keyword){
        if (keyword.isEmpty()){
            return userRepository.findAll(PageRequest.of(page, taille));
        }
        Page<User> result = userRepository.findByUsernameContains(keyword, PageRequest.of(page, taille));
        if (result.isEmpty()) {
            return Page.empty(PageRequest.of(page, taille));
        }
        return result;
    }

    public User addUser(User user){
        if (user != null && userRepository.findByUsername(user.getUsername()) == null) {
            return userRepository.save(user);
        }
        return null;
    }

    public boolean addLikedSong(Long userId, Long songId){
        if (userId != null && songId != null &&
                userRepository.existsById(userId) &&
                songRepository.existsById(songId)) {

            User user = userRepository.findById(userId).get();
            Song song = songRepository.findById(songId).get();

            if (!user.getLikedSongs().contains(song)) {
                user.getLikedSongs().add(song);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    public boolean removeLikedSong(Long userId, Long songId){
        if (userId != null && songId != null &&
                userRepository.existsById(userId) &&
                songRepository.existsById(songId)) {

            User user = userRepository.findById(userId).get();
            Song song = songRepository.findById(songId).get();

            if (user.getLikedSongs().contains(song)) {
                user.getLikedSongs().remove(song);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    public List<User> findFollowersByArtistId(Long artistId) {
        if (artistId != null && artistRepository.existsById(artistId)) {
            Artist artist = artistRepository.findById(artistId).get();
            return new ArrayList<>(artist.getFollowers());
        }
        return new ArrayList<>();
    }

    public boolean addFollowing(Long userId, Long artistId){
        if (userId != null && artistId != null &&
                userRepository.existsById(userId) &&
                artistRepository.existsById(artistId)) {

            User user = userRepository.findById(userId).get();
            Artist artist = artistRepository.findById(artistId).get();

            if (!user.getFollowings().contains(artist)) {
                user.getFollowings().add(artist);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    public boolean removeFollowing(Long userId, Long artistId){
        if (userId != null && artistId != null &&
                userRepository.existsById(userId) &&
                artistRepository.existsById(artistId)) {

            User user = userRepository.findById(userId).get();
            Artist artist = artistRepository.findById(artistId).get();

            if (user.getFollowings().contains(artist)) {
                user.getFollowings().remove(artist);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}
