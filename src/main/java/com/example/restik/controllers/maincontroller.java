package com.example.restik.controllers;

import com.example.restik.models.message;
import com.example.restik.models.news;
import com.example.restik.models.user;
import com.example.restik.repository.commentrepository;
import com.example.restik.repository.likesrepository;
import com.example.restik.repository.newsrepository;
import com.example.restik.repository.userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

@Controller
public class maincontroller {

    @Autowired
    private newsrepository newsrepository;
    @Autowired
    private userrepository userrepository;
    @Autowired
    private likesrepository likesrepository;
    @Autowired
    private commentrepository commentrepository;

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
        model.addAttribute("avatar",userrepository.findByUsername(currentPrincipalName).getAvatarlink());

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);

        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        df2.setTimeZone(timezone);
        model.addAttribute("df2",df2);

        Long userid = userrepository.findByUsername(currentPrincipalName).getId();
        Iterable<news> listnews = newsrepository.findByAuthor_idOrderByDateDesc(userid);
        model.addAttribute("news",listnews);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("newsrep",newsrepository);
        model.addAttribute("commrep",commentrepository);
        model.addAttribute("likerep",likesrepository);

        model.addAttribute("curusname",currentPrincipalName);

        return "myprofile";
    }

    @PostMapping("/prf/chng_avtr")
    public String avtr(@RequestParam String avatarlink, user user) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        user= userrepository.findByUsername(currentPrincipalName);
        user.setAvatarlink(avatarlink);
        userrepository.save(user);
        return "redirect:/prf/";
    }
    @GetMapping("/yandex_5795c5555457349c.html")
    public String yandex(Model model) {
        return "yandex_5795c5555457349c";
    }


}
