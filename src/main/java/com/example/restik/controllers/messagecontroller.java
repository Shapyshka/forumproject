package com.example.restik.controllers;

import com.example.restik.models.comment;
import com.example.restik.models.message;
import com.example.restik.models.news;
import com.example.restik.repository.messagerepository;
import com.example.restik.repository.newsrepository;
import com.example.restik.repository.userrepository;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Stream;

@Controller // This means that this class is a Controller
@RequestMapping(path="/msg/") // This means URL's start with /demo (after Application path)
public class messagecontroller {


    @Autowired
    private userrepository userrepository;

    @Autowired
    private messagerepository messagerepository;


    @GetMapping(path = "/")
    public String msg(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Long curuserid = userrepository.findByUsername(currentPrincipalName).getId();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        df2.setTimeZone(TimeZone.getDefault());
        model.addAttribute("df2",df2);

        Iterable<message> listmsg = messagerepository.findByFrom_idOrTo_idOrderByDateDesc(curuserid,curuserid);
        //ArrayList<Long> list = new ArrayList<>();
        HashMap<Long,String> map = new HashMap<>();

        for(message m:listmsg){
            if(Objects.equals(m.getFrom().getId(), curuserid)){
                if(!map.containsKey(m.getTo().getId()))
                    map.put(m.getTo().getId(),userrepository.findById(m.getTo().getId()).get().getUsername());
            }

            else if(Objects.equals(m.getTo().getId(), curuserid)){
                if(!map.containsKey(m.getFrom().getId()))
                    map.put(m.getFrom().getId(),userrepository.findById(m.getFrom().getId()).get().getUsername());
            }
        }

        model.addAttribute("talkto",map);
        return "messages";
    }
    @GetMapping(path = "/{id}")
    public String msgwithuser(@PathVariable("id") String msgwith, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Long curuserid = userrepository.findByUsername(currentPrincipalName).getId();
        Long hisid = userrepository.findByUsername(msgwith).getId();

        model.addAttribute("usrnm",msgwith);

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("ru", "RU"));
        df1.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Greenwich")));
        model.addAttribute("df1",df1);

        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy HH:mm",new Locale("ru", "RU"));
        df2.setTimeZone(TimeZone.getDefault());
        model.addAttribute("df2",df2);

        List<message> listmsg1 = messagerepository.findFromMeToDude(userrepository.findById(curuserid), userrepository.findById(hisid), Sort.by(Sort.Direction.DESC, "date"));
        List<message> listmsg2 = messagerepository.findToMeFromDude(userrepository.findById(curuserid), userrepository.findById(hisid), Sort.by(Sort.Direction.DESC, "date"));
        //TODO sort this shit by the date
        List<message> allmsgs = new ArrayList<>(Stream.concat(listmsg1.stream(), listmsg2.stream()).toList());

        allmsgs.sort(Collections.reverseOrder());
        model.addAttribute("msgs",allmsgs);
        return "messagesView";
    }
    @PostMapping("/{id}/sndmsg")
    public String sendmsg(@Valid message message, BindingResult bindingResult, @PathVariable("id") String id) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

//        if(bindingResult.hasErrors())
//            return "redirect:/msg/"+id;

        message.setFrom(userrepository.findByUsername(currentPrincipalName));
        message.setTo(userrepository.findByUsername(id));

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",new Locale("ru", "RU"));
        Date now = df1.parse(ZonedDateTime.now(ZoneId.of("Greenwich")).toString().replace("T"," "));
        message.setDate(now);

        messagerepository.save(message);
        return "redirect:/msg/"+id;
    }
}