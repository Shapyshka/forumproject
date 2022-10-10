package com.example.restik.controllers;

import com.example.restik.models.news;
import com.example.restik.repository.newsrepository;
import com.example.restik.repository.userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.restik.models.user;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Controller // This means that this class is a Controller
@RequestMapping(path="/nws/") // This means URL's start with /demo (after Application path)
public class newscontroller {

    @Autowired
    private newsrepository newsrepository;
    @Autowired
    private userrepository userrepository;

    @GetMapping(path="/")
    public String homenews(Model model) throws ParseException {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);

        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        df2.setTimeZone(TimeZone.getDefault());
        model.addAttribute("df2",df2);

        Iterable<news> listnews = newsrepository.findAll();
        model.addAttribute("news",listnews);
//        model.addAttribute("userid",username);

        return "newsList";
    }
    @GetMapping("/crt_nws")
    public String addnews(news news){

        return "newsAdd";
    }
    @PostMapping("/crt_nws")
    public String savenews(@Valid news news, BindingResult bindingResult) throws ParseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if(bindingResult.hasErrors())
            return "newsAdd";
        news.setAuthor(userrepository.findByUsername(currentPrincipalName));

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",new Locale("ru", "RU"));
        Date now = df1.parse(ZonedDateTime.now(ZoneId.of("Greenwich")).toString().replace("T"," "));
        news.setDate(now);

        newsrepository.save(news);

        return "redirect:/nws/";
    }

    @GetMapping("/{id}")
    public String viewnews(@PathVariable("id") Long id, Model model){

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);

        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        df2.setTimeZone(TimeZone.getDefault());
        model.addAttribute("df2",df2);

        Optional<news> onenews= newsrepository.findById(id);
        ArrayList<news> res = new ArrayList<>();
        onenews.ifPresent(res::add);
        model.addAttribute("news",res);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("curusname",currentPrincipalName);

        return "newsView";
    }

    @GetMapping("/{id}/edt_nws")
    public String editview(@PathVariable("id")Long id, news news, Model model){
        Optional<news> onenews= newsrepository.findById(id);
        ArrayList<news> res = new ArrayList<>();
        onenews.ifPresent(res::add);
        model.addAttribute("onenew",res);
        return "newsEdit";
    }

    @PostMapping("/{id}/edt_nws")
    public String editview(@Valid news news, BindingResult bindingResult, @PathVariable("id") Long id, Model model)   {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<news> onenews= newsrepository.findById(id);
        ArrayList<news> res = new ArrayList<>();
        onenews.ifPresent(res::add);
        model.addAttribute("onenew",res);
        news.setAuthor(userrepository.findByUsername(currentPrincipalName));
        if(bindingResult.hasErrors()) {
            return "newsEdit";
        }

        newsrepository.save(news);
        return ("redirect:/nws/{id}");
    }

    @PostMapping("/{id}/del")
    public String delnews(@PathVariable("id") Long id, Model model){
        news news= newsrepository.findById(id).orElseThrow();
        newsrepository.delete(news);
        return ("redirect:/nws/");
    }
}
