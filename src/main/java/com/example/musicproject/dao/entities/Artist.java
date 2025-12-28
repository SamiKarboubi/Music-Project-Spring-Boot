package com.example.musicproject.dao.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artist extends Person {
    private String bio;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_ARTIST;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Album> albums = new ArrayList<>();

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    private Collection<Song> songs = new ArrayList<>();

    @ManyToMany(mappedBy = "followings", fetch = FetchType.LAZY)
    private Collection<User> followers = new ArrayList<>();
}
