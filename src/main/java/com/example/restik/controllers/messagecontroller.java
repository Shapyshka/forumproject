package com.example.restik.controllers;

import com.example.restik.models.news;
import com.example.restik.repository.newsrepository;
import com.example.restik.repository.userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // This means that this class is a Controller
@RequestMapping(path="/msg/") // This means URL's start with /demo (after Application path)
public class messagecontroller {


    @Autowired
    private com.example.restik.repository.userrepository userrepository;

    @GetMapping(path = "/")
    public String msg(Model model) {
//        Iterable<news> listnews = newsrepository.findAll();
//        model.addAttribute("news", listnews);

        return "messages";
    }
}