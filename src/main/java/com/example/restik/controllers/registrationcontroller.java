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
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Objects;

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

    public static boolean isValid(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.UnicodeBlock.of(s.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                return false;
            }
        }
        return true;
    }
    @PostMapping("/registration")
    public String newuser(@RequestParam String password2, user user, Model model){

        if(!isValid(user.getUsername())){
            model.addAttribute("usern",user.getUsername());
            model.addAttribute("userp",user.getPassword());

            model.addAttribute("message","В име пользователя недопустима кириллица");
            return "registration";
        }

        if(user.getUsername().length()==0){
            model.addAttribute("usern",user.getUsername());
            model.addAttribute("userp",user.getPassword());

            model.addAttribute("message","Введите имя пользователя");
            return "registration";
        }

        if(!Objects.equals(user.getPassword(), password2)){
            model.addAttribute("usern",user.getUsername());
            model.addAttribute("userp",user.getPassword());

            model.addAttribute("message","Пароли не совпадают");
            return "registration";
        }

        if(user.getPassword().length()<8){
            model.addAttribute("usern",user.getUsername());

            model.addAttribute("message","Придумайте пароль длиной минимум 8 символов");
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
