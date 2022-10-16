package com.example.restik.controllers;

import com.example.restik.models.news;
import com.example.restik.repository.newsrepository;
import com.example.restik.repository.userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

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
    //@RequestMapping
    public String profile(TimeZone timezone,  Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("username",currentPrincipalName);

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);

        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        df2.setTimeZone(timezone);
        model.addAttribute("df2",df2);

        Long userid = userrepository.findByUsername(currentPrincipalName).getId();
        Iterable<news> listnews = newsrepository.findByAuthor_idOrderByDateDesc(userid);
        model.addAttribute("news",listnews);
        return "myprofile";
    }
    @GetMapping("/usr/{id}")
    //@RequestMapping
    public String user(@PathVariable("id") String usern, TimeZone timezone, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        String username = userrepository.findByUsername(usern).getUsername();
        if(Objects.equals(username, currentPrincipalName))
            return "redirect:/prf";
        model.addAttribute("username",username);


        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);

        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        df2.setTimeZone(timezone);
        model.addAttribute("df2",df2);

        Long userid = userrepository.findByUsername(usern).getId();
        Iterable<news> listnews = newsrepository.findByAuthor_idOrderByDateDesc(userid);
        model.addAttribute("news",listnews);

        return "userprofile";
    }

}
