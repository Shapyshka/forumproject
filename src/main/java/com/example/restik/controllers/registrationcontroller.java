package com.example.restik.controllers;


import com.example.restik.models.news;
import com.example.restik.models.role;
import com.example.restik.models.user;
import com.example.restik.repository.userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class registrationcontroller {
    @Autowired
    userrepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @PostMapping("/registration")
    public String newuser(user user, Model model){

        if(user.getUsername().length()==0){
            model.addAttribute("message","Введите имя пользователя");
            return "registration";
        }
        if(user.getPassword().length()<8){
            model.addAttribute("message","Придумайте пароль длинной в минимум 8 символов");
            return "registration";
        }

        user userFromDb=userRepository.findByUsername(user.getUsername());
        if(userFromDb!=null){
            model.addAttribute("message","Данное имя пользователя уже занято");
            return "registration";
        }
        user.setActive(true);
        user.setAvatarlink("http://my-engine.ru/modules/users/avatar.png");
        user.setRoles(Collections.singleton(role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }

}
