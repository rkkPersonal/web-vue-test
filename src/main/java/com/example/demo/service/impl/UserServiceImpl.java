package com.example.demo.service.impl;

import com.example.demo.service.UserService;
import com.example.demo.test.mockito.UserMappper;
import com.example.demo.vo.Response;
import com.example.demo.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMappper userMappper;

    @Override
    public Response getUserInfo(UserVo userVo) {
        System.out.println("hello strange users");
        String username = userMappper.selectUsersByUsername(userVo.getUserName());
        return Response.ok();
    }

    @Override
    public String registe(String username) {

        return username;
    }
}
