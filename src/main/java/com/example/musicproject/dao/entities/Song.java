package com.example.musicproject.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Date releaseDate;
    private String genre;
    private String audioPath;
    private int durationInSeconds;

    @ManyToOne
    private Artist artist;

    @ManyToOne
    private Album album;

    @ManyToMany(mappedBy = "songs")

    private Collection<Playlist> playlists = new ArrayList<>();

    @ManyToMany(mappedBy = "likedSongs")

    private Collection<User> users = new ArrayList<>();
}
