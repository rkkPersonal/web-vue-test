package com.example.demo.mock;

import com.alibaba.fastjson.JSON;
import com.example.demo.service.StudyUnitService;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.StudyJunitServiceImpl;
import com.example.demo.test.mockito.UserMappper;
import com.example.demo.utils.MockitoStaticUtil;
import com.example.demo.vo.Response;
import com.example.demo.vo.UserVo;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRule;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UnitTestPractice {

    @InjectMocks
    private StudyJunitServiceImpl studyUnitService;
    @Mock
    private UserMappper userMappper;
    @Mock
    private UserService userService;
    @Mock
    private RestTemplate restTemplate;

    @Mock
    HashMap<String, Integer> hashMap;
    @Captor
    ArgumentCaptor<String> keyCaptor;

    @Captor
    ArgumentCaptor<Integer> valueCaptor;

    @After
    public void init() {

        Mockito.reset(userService, userMappper, restTemplate);

    }

    @Test
    public void testStudyService() {


        UserVo userVo = getUserVo();


        Mockito.when(userMappper.selectUsersByUsername(userVo.getUserName())).thenReturn("查询用户信息");
        List<String> list = new ArrayList<>();

        Response ok = Response.ok();
        Mockito.when(userService.getUserInfo(userVo)).thenReturn(ok);

        Mockito.when(userService.registe(userVo.getUserName())).thenReturn("Peck注册成功");
        Response userInfo = userService.getUserInfo(userVo);
        System.out.println(JSON.toJSONString(userInfo));
        verify(userService, Mockito.times(1)).getUserInfo(userVo);

        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        Mockito.when(restTemplate.getForObject("http://www.test.com", String.class, objectObjectHashMap)).thenReturn("success");

        Mockito.when(restTemplate.getForObject("http://www.sxr.com", String.class, objectObjectHashMap)).thenReturn("passed");


        Mockito.when(studyUnitService.studyMethod2("Steven", 1, list)).thenReturn("ForTesting");

        Mockito.when(studyUnitService.studyMethod1("Steven", 1, list)).thenCallRealMethod();

        String s = studyUnitService.studyMethod1("Steven", 1, list);
        Assert.assertEquals(s, "Hello Study !");





        /*ReflectionTestUtils.setField(studyUnitService,"sss","value");*/
    }


    private UserVo getUserVo() {
        UserVo userVo = new UserVo();
        userVo.setUserId(2L);
        userVo.setUserName("Steven");
        userVo.setEmail("1325435@qq.com");
        return userVo;
    }


    @Test
    public void saveTest() {
        hashMap.put("A", 10);

        Mockito.verify(hashMap).put(keyCaptor.capture(), valueCaptor.capture());

        assertEquals("A", keyCaptor.getValue());
        assertEquals(new Integer(10), valueCaptor.getValue());

    }


    /**
     * 如果有 循环100个 list的数字，可以用这种方式测试
     */
    @Test
    public void testStubbing() {

        List<String> list = Mockito.mock(List.class);

        Mockito.when(list.get(anyInt())).thenAnswer(invocationOnMock -> {
            Integer index = invocationOnMock.getArgumentAt(0, Integer.class);
            return String.valueOf(index * 10);
        });


        Assert.assertThat(list.get(1), equalTo("10"));
        Assert.assertThat(list.get(999), equalTo("9990"));
    }

}
