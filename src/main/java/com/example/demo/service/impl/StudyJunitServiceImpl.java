package com.example.demo.service.impl;

import com.example.demo.service.StudyUnitService;
import com.example.demo.service.UserService;
import com.example.demo.test.mockito.UserMappper;
import com.example.demo.utils.MockitoStaticUtil;
import com.example.demo.vo.Response;
import com.example.demo.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Service
@Getter
public class StudyJunitServiceImpl implements StudyUnitService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMappper userMappper;

    private RestTemplate restTemplate = new RestTemplate();


    @Override
    public String studyMethod1(String s, int i, List<?> list) {

        String username = userMappper.selectUsersByUsername(s);
        System.out.println("username:" + username);
        if ("Steven".equals(username)) {


        }

        UserVo userVo = new UserVo();
        userVo.setUserId(2L);
        userVo.setUserName("Steven");
        userVo.setEmail("1325435@qq.com");
        Response userInfo = userService.getUserInfo(userVo);

        String registe = userService.registe(s);

        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        String forObject = restTemplate.getForObject("http://www.test.com", String.class, objectObjectHashMap);

        System.out.println(forObject);
        String s1 = studyMethod2(s, i, list);
        System.out.println("s1="+s1);

        String steven = MockitoStaticUtil.testMethod1("Steven");
        System.out.println("Mockito StaticUtil ="+steven);
        return "Hello Study !";
    }

    @Override
    public String studyMethod2(String s, int i, List<?> list) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        String forObject = restTemplate.getForObject("http://www.sxr.com", String.class, objectObjectHashMap);
        return forObject;
    }
}
