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
import java.util.ArrayList;
import java.util.Optional;

@Controller // This means that this class is a Controller
@RequestMapping(path="/nws/") // This means URL's start with /demo (after Application path)
public class newscontroller {

    @Autowired
    private newsrepository newsrepository;
    @Autowired
    private userrepository userrepository;

    @GetMapping(path="/")
    public String homenews(Model model){
        Iterable<news> listnews = newsrepository.findAll();
        model.addAttribute("news",listnews);
        return "newsList";
    }
    @GetMapping("/crt_nws")
    public String addnews(news news){

        return "newsAdd";
    }
    @PostMapping("/crt_nws")
    public String savenews(@Valid news news, BindingResult bindingResult){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if(bindingResult.hasErrors())
            return "newsAdd";

        news.setAuthor(userrepository.findByUsername(currentPrincipalName));

        newsrepository.save(news);

        return "redirect:/nws/";
    }

    @GetMapping("/{id}")
    public String viewnews(@PathVariable("id") Long id, Model model){
        Optional<news> onenews= newsrepository.findById(id);
        ArrayList<news> res = new ArrayList<>();
        onenews.ifPresent(res::add);
        model.addAttribute("news",res);
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
