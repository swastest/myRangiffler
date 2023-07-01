package org.rangiffler.rangifflerusers.controller;

import org.rangiffler.rangifflerusers.model.UserJson;
import org.rangiffler.rangifflerusers.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UsersService userService;

    @Autowired
    public UserController(UsersService userService) {
        this.userService = userService;
    }

    @PatchMapping("/updateUserInfo")
    public UserJson updateUserInfo(@RequestBody UserJson user) {
        return userService.update(user);
    }

    @GetMapping("/currentUser")
    public UserJson currentUser(@RequestParam String username) {
        return userService.getCurrentUserOrCreateIfAbsent(username);
    }

    @GetMapping("/allUsers")
    public List<UserJson> allUsers(@RequestParam String username) {
        return userService.allUsers(username);
    }
}
