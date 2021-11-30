package com.example.demo.controller;

import com.example.demo.annotation.Authorized;
import com.example.demo.service.UserService;
import com.example.demo.vo.Order;
import com.example.demo.vo.Response;
import com.example.demo.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Steven
 */
@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/rest/user")
    public Response getUserInfo(@RequestBody UserVo userVo){
        return userService.getUserInfo(userVo);

    }



}
