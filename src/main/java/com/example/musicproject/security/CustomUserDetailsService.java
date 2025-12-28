package com.example.musicproject.security;

import com.example.musicproject.dao.entities.Artist;
import com.example.musicproject.dao.entities.User;
import com.example.musicproject.dao.repositories.ArtistRepository;
import com.example.musicproject.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public class CostumUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username);
        if(user != null){
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    Collections.singleton(()->user.getRole().name())
            );
        }
        Artist artist = artistRepository.findByUsername(username);
        if(artist != null){
            return new org.springframework.security.core.userdetails.User(
                    artist.getUsername(),
                    artist.getPassword(),
                    Collections.singleton(()->artist.getRole().name())
            );
        }
        throw new UsernameNotFoundException("User not found");
    }
}
