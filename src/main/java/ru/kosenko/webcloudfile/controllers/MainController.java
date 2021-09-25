package ru.kosenko.webcloudfile.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.kosenko.webcloudfile.entities.User;
import ru.kosenko.webcloudfile.services.UserService;

import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class MainController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/authenticated")
    public String pageForAuthenticatedUsers(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return "Страница пользователя: " + user.getUsername() + " "
                + user.getEmail(); // Имя пользователя и Email
    }

    @GetMapping("/adminpanel")
    public String pageForAdminpanel() {
        return "Страница администратора";
    }
}
