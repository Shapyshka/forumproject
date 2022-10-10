package com.example.restik.controllers;

import com.example.restik.repository.newsrepository;
import com.example.restik.repository.userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class maincontroller {

    @Autowired
    private com.example.restik.repository.newsrepository newsrepository;
    @Autowired
    private com.example.restik.repository.userrepository userrepository;

    @GetMapping("/")
    public String home(Model model) {
        return "redirect:/nws/";
    }
    @GetMapping("/prf")
    public String profile(  Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("username",currentPrincipalName);
        return "myprofile";
    }
    @GetMapping("/usr/{id}")
    public String user(@PathVariable("id") String userid, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        String username = userrepository.findByUsername(userid).getUsername();
        if(Objects.equals(username, currentPrincipalName))
            return "redirect:/prf";
        model.addAttribute("username",username);
        return "userprofile";
    }

}
