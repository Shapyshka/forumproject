package com.example.restik.controllers;

import com.example.restik.models.comment;
import com.example.restik.models.likes;
import com.example.restik.models.news;
import com.example.restik.repository.commentrepository;
import com.example.restik.repository.likesrepository;
import com.example.restik.repository.newsrepository;
import com.example.restik.repository.userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.restik.models.user;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private commentrepository commentrepository;
    @Autowired
    private likesrepository likesrepository;

    @GetMapping(path="/")
//    @RequestMapping
    public String homenews(Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        model.addAttribute("df2",df2);

        Iterable<news> listnews = newsrepository.findAllByOrderByDateDesc();
        model.addAttribute("news",listnews);

        model.addAttribute("userrep",userrepository);
        model.addAttribute("newsrep",newsrepository);
        model.addAttribute("commrep",commentrepository);
        model.addAttribute("likerep",likesrepository);

        model.addAttribute("curusname",currentPrincipalName);
        model.addAttribute("curuserid",userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId()));
        return "newsList";
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
//    @RequestMapping
    public String viewnews(@PathVariable("id") Long id, TimeZone timezone, Model model){

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);

        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        df2.setTimeZone(timezone);
        model.addAttribute("df2",df2);

        Optional<news> onenews= newsrepository.findById(id);
        ArrayList<news> res = new ArrayList<>();
        onenews.ifPresent(res::add);
        model.addAttribute("news",res);

        Iterable<comment> listcomments = commentrepository.findByZapis_idOrderByDateDesc(id);
        model.addAttribute("comments",listcomments);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        model.addAttribute("userrep",userrepository);
        model.addAttribute("newsrep",newsrepository);
        model.addAttribute("commrep",commentrepository);
        model.addAttribute("likerep",likesrepository);

        model.addAttribute("curusname",currentPrincipalName);
        model.addAttribute("curuserid",userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId()));

        return "newsView";
    }

    @GetMapping("/{id}/edt_nws")
    public String editview(@PathVariable("id")Long id, news news, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<news> onenews= newsrepository.findById(id);
        ArrayList<news> res = new ArrayList<>();

        if(!Objects.equals(onenews.get().getAuthorName(), currentPrincipalName))
            return "redirect:/nws/"+id.toString();

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
        news.setDate(newsrepository.findById(news.getId()).get().getDate());
        if(bindingResult.hasErrors()) {
            return "newsEdit";
        }

        newsrepository.save(news);
        return ("redirect:/nws/{id}");
    }
    @PostMapping("/{id}/cmnt")
    public String comment(@Valid comment comment, BindingResult bindingResult, @PathVariable("id") Long id) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        if(bindingResult.hasErrors())
            return "newsView";
        comment.setAuthor(userrepository.findByUsername(currentPrincipalName));
        comment.setZapis(newsrepository.findById(id).orElseThrow());
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",new Locale("ru", "RU"));
        Date now = df1.parse(ZonedDateTime.now(ZoneId.of("Greenwich")).toString().replace("T"," "));
        comment.setDate(now);
        commentrepository.save(comment);
        return "redirect:/nws/"+id;
    }


    @PostMapping("/{id}/like")
    @ResponseStatus(value = HttpStatus.OK)
    public void like(Model model, likes likes, BindingResult bindingResult, @PathVariable("id") Long id) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if(likesrepository.findLikes(newsrepository.findById(id),userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId())).size()!=0) {
            likes like = likesrepository.findLikes(newsrepository.findById(id),userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId())).get(0);
            likesrepository.delete(like);
        }
        else{
            if(likesrepository.findDises(newsrepository.findById(id),userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId())).size()!=0){
                likes like = likesrepository.findDises(newsrepository.findById(id),userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId())).get(0);
                likesrepository.delete(like);
            }
            likes.setUserliked(userrepository.findByUsername(currentPrincipalName));
            likes.setZapis(newsrepository.findById(id).orElseThrow());
            likes.setLikeornot(true);
            likesrepository.save(likes);
        }
        //return "redirect:/nws/";
    }

    @PostMapping("/{id}/dislike")
    @ResponseStatus(value = HttpStatus.OK)
    public void dislike(Model model, likes likes, BindingResult bindingResult, @PathVariable("id") Long id) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        if(likesrepository.findDises(newsrepository.findById(id),userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId())).size()!=0) {
            likes like = likesrepository.findDises(newsrepository.findById(id),userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId())).get(0);
            likesrepository.delete(like);
        }
        else{
            if(likesrepository.findLikes(newsrepository.findById(id),userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId())).size()!=0){
                likes like = likesrepository.findLikes(newsrepository.findById(id),userrepository.findById(userrepository.findByUsername(currentPrincipalName).getId())).get(0);
                likesrepository.delete(like);
            }
            likes.setUserliked(userrepository.findByUsername(currentPrincipalName));
            likes.setZapis(newsrepository.findById(id).orElseThrow());
            likes.setLikeornot(false);
            likesrepository.save(likes);
        }

        //return "redirect:/nws/";
    }

    @PostMapping("/{id}/del")
    public String delnews(@PathVariable("id") Long id, Model model){
        Iterable<comment> comments= commentrepository.findByZapis_idOrderByDateDesc(id);
        for(comment com:comments)
            commentrepository.delete(com);

        List<likes> likes= likesrepository.findByZapis_id(id);
        for(likes like:likes)
            likesrepository.delete(like);

        news news= newsrepository.findById(id).orElseThrow();
        newsrepository.delete(news);
        return ("redirect:/nws/");
    }
}
