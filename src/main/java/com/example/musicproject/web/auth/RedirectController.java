package com.example.musicproject.web.auth;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/redirect")
    public String redirectAfterLogin(Authentication auth){
        if(auth.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"))){
            return "redirect:/admin/dashboard";
        }else if(auth.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ARTIST"))){
            return "redirect:/artist/dashboard";
        }else{
            return "redirect:/user/home";
        }
    }
}
