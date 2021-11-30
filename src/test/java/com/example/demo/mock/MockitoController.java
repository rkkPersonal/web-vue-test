package com.example.demo.mock;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.controller.UserController;
import com.example.demo.service.UserService;
import com.example.demo.vo.Response;
import com.example.demo.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
@AutoConfigureMockMvc
// @SpringBootTest // 注入所有bean上下文
@WebMvcTest(UserController.class) // 轻量级，加载bean更少，
@ExtendWith(SpringExtension.class)
public class MockitoController {


    @Resource
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    public void getUserInfo() throws Exception {


        UserVo userVo = new UserVo();
        userVo.setUserId(2L);
        userVo.setUserName("steven");
        userVo.setEmail("1325435@qq.com");

        String request = JSONObject.toJSONString(userVo);
        Mockito.when(userService.getUserInfo(userVo)).thenReturn(new Response(1,"success",userVo));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.request(HttpMethod.GET, "/rest/user")
                        .contentType("application/json").content(request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userName").value("steven"))
                .andDo(print()).andReturn();


        mvcResult.getResponse().setCharacterEncoding("utf-8");
        log.info(mvcResult.getResponse().getContentAsString());

    }
}
