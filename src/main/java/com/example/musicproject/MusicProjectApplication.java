package com.example.musicproject;

import com.example.musicproject.dao.entities.*;
import com.example.musicproject.dao.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class MusicProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicProjectApplication.class, args);
    }


}
