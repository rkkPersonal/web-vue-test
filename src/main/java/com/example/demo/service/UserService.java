package com.example.demo.service;

import com.example.demo.vo.Response;
import com.example.demo.vo.UserVo;

import javax.xml.transform.Result;

/**
 * @author Steven
 */
public interface UserService {

    public Response getUserInfo(UserVo userVo);

    public String registe(String username);
}
