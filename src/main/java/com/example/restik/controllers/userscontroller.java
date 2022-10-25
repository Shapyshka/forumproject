package com.example.restik.controllers;

import com.example.restik.models.message;
import com.example.restik.models.news;
import com.example.restik.models.user;
import com.example.restik.repository.newsrepository;
import com.example.restik.repository.userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Stream;

@Controller
@RequestMapping(path="/usrs/")
public class userscontroller {


    @Autowired
    private userrepository userrepository;
    @Autowired
    private newsrepository newsrepository;

    @GetMapping(path = "/")
    public String homeusers(Model model) throws ParseException {
        List<user> listusers = userrepository.findAll();
        Collections.reverse(listusers);
        model.addAttribute("users", listusers);
        return "userlist";
    }

    @GetMapping("/{id}")
    public String user(@PathVariable("id") String usern, TimeZone timezone, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        String username = userrepository.findByUsername(usern).getUsername();
        if(Objects.equals(username, currentPrincipalName))
            return "redirect:/prf";

        model.addAttribute("username",username);
        model.addAttribute("avatar",userrepository.findByUsername(username).getAvatarlink());

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);

        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        df2.setTimeZone(timezone);
        model.addAttribute("df2",df2);

        Long userid = userrepository.findByUsername(usern).getId();
        Iterable<news> listnews = newsrepository.findByAuthor_idOrderByDateDesc(userid);
        model.addAttribute("news",listnews);
        model.addAttribute("userrep",userrepository);

        return "userprofile";
    }
}