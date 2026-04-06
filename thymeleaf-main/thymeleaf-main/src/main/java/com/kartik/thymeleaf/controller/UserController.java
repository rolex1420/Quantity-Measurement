package com.kartik.thymeleaf.controller;


import com.kartik.thymeleaf.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {




    @GetMapping("variable-expression")
    public String variableExpression(Model model)
    {
        User user = new User("Kartik","kartiks04800@gmail.com","ADMIN","Male");
        model.addAttribute("user",user);
        return "variable-expression";
    }


    @GetMapping("selection-expression")
    public String selectionExpression(Model model)
    {
        User user = new User("Kartik","kartiks04800@gmail.com","ADMIN","MALE");
        model.addAttribute("user",user);
        return "selection-expression";
    }

    @GetMapping("message-expression")

    public String messageExpression()
    {
        return "message-expression";
    }

    @GetMapping("link-expression")
    public String linkExpression(Model model)
    {
        model.addAttribute("id",1);
        return "link-expression";
    }


    @GetMapping("fragment-expression")
    public String fragmentExpression(){
        return "fragment-expression";
    }

    @GetMapping("/users")
    public String user(Model model){
        User admin = new User("Kartik","kartiks04800@gmail.com","ADMIN","Male");
        User xyz = new User("XYZ","xyz@gmail.com","ADMIN","Male");
        User kgf = new User("KGF","kgf@gmail.com","ADMIN","Male");

        List<User> users = new ArrayList<>();
        users.add(admin);
        users.add(xyz);
        users.add(kgf);
        model.addAttribute("users",users);
        return "users";
    }


    @GetMapping("/if-unless")
    public String ifUnless(Model model)
    {
        User admin = new User("Kartik","kartiks04800@gmail.com","ADMIN","Male");
        User xyz = new User("XYZ","xyz@gmail.com","USER","Male");
        User kgf = new User("KGF","kgf@gmail.com","USER","Male");

        List<User> users = new ArrayList<>();
        users.add(admin);
        users.add(xyz);
        users.add(kgf);
        model.addAttribute("users",users);
        return "if-unless";
    }

    @GetMapping("/switch-case")
    public String switchCase(Model model)
    {
        User user = new User("Kartik","kartiks04800@gmail.com","GUEST","Male");
        model.addAttribute("user",user);
        return "switch-case";
    }

}
