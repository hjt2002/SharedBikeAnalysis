package com.example.sharedbike.controller;

import com.example.sharedbike.entity.User;
import com.example.sharedbike.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    final
    UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/findAll")
    public List<User> findAll(){
        return userService.findALL();
    }

    @PostMapping("/checkAccount")
    public Map<String, Object> checkAccount(@RequestBody Map<String,Object>map){
        String userid = map.get("userid").toString();
        String password = map.get("password").toString();
        return userService.login(userid,password);
    }

    @PostMapping("/registerAccount")
    public String registerAccount(@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("deleteAccount")
    public String deleteAccount(@RequestBody Map<String,Object> map){
        return userService.deleteAccount(map.get("userid").toString());
    }
    @PostMapping("modifyAccount")
    public String modifyAccount(@RequestBody Map<String,Object> map){
        String userid,username,password,email;

        if (map.get("userid") != null ){
            userid = map.get("userid").toString();
        }else {
            userid = null;
        }

        if (map.get("username") != null){
            username = map.get("username").toString();
        }else {
            username = null;
        }
        if (map.get("password") != null){

            password = map.get("password").toString();
        }else {
            password = null;
        }

        if (map.get("email") != null){
            email = map.get("email").toString();
        }else {
            email = null;
        }

        return userService.modify(userid,username,password,email);
    }
}
