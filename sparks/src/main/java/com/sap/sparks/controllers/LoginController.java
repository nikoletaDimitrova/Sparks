package com.sap.sparks.controllers;

import com.sap.sparks.entity.Users;
import com.sap.sparks.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.GenericWebApplicationContext;

@RestController
public class LoginController {

    @Autowired
    UsersService usersService;
    @Autowired
    GenericWebApplicationContext context;

//    @GetMapping("/")
//    public String index(Model model){
//        return "index";
//    }

    @PostMapping("/login")
    public UserDetails loginForm(@RequestBody Users user){
        if(user == null) {
            //exception
        }
        return usersService.loadUserByUsername(user.getUserName());
    }

    @GetMapping("/home")
    public String home(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersService.findByUserName(auth.getName());
        model.addAttribute("user", user);
        return "home";
    }

}
