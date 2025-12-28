package com.example.musicproject.web.auth;


import com.example.musicproject.dao.entities.Artist;
import com.example.musicproject.dao.entities.Role;
import com.example.musicproject.dao.entities.User;
import com.example.musicproject.dao.repositories.ArtistRepository;
import com.example.musicproject.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "auth/login";
    }

    @GetMapping("/register")
    public String chooseRegisterType(){
        return "auth/choose";
    }

    @GetMapping("/register/user")
    public String showUserRegisterForm(Model model){
        model.addAttribute("user", new User());
        return "auth/register_user";
    }

    @PostMapping("/register/user")
    public String registerUser(@ModelAttribute("user") User user,Model model){
        if(userRepository.findByUsername(user.getUsername())!=null){
            model.addAttribute("error","Nom d'utilisateur déja pris.");
            return "auth/register_user";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/register/artist")
    public String showArtistRegisterPage(Model model) {
        model.addAttribute("artist", new Artist());
        return "auth/register_artist";
    }

    @PostMapping("/register/artist")
    public String registerArtist(@ModelAttribute("artist") Artist artist, Model model) {
        if (artistRepository.findByUsername(artist.getUsername()) != null) {
            model.addAttribute("error", "Nom d'artiste déjà pris.");
            return "auth/register_artist";
        }

        artist.setPassword(passwordEncoder.encode(artist.getPassword()));
        artist.setRole(Role.ROLE_ARTIST);
        artistRepository.save(artist);
        return "redirect:/";
    }

}
