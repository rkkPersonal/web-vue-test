package com.example.demo.controller;

import com.example.demo.utils.CookieUtil;
import com.example.demo.utils.ThreadLocalUtil;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin(allowCredentials = "true",allowedHeaders = "*",maxAge = 36000)
@RestController
public class DemoController {

    private Map<String, User> userMap = new ConcurrentHashMap<>();

    @GetMapping("/user")
    public Result getMethod(HttpServletResponse response) {
        ThreadLocalUtil.add(response);
        User user = new User();
        user.setName("steven");
        user.setAddress(UUID.randomUUID().toString().replaceAll("-", ""));
        System.out.println(user.toString());
        CookieUtil.newCookie(user.getName());
        return Result.ok(user);
    }


    @PostMapping("/user/post")
    public Result postMethod(HttpServletRequest request) {
        String cookieValue = CookieUtil.getCookieValue(request);
        System.out.println(cookieValue);
        return Result.ok(cookieValue);
    }


    @GetMapping("/getUserinfo")
    public Result getUserInfo(HttpServletRequest request) {
        System.out.println(request.getSession().getId());
        request.getSession().setAttribute("steven",request.getSession().getId());
        List<User> objects = new ArrayList<>();
        CollectionUtils.addAll(objects, new User("steven", "山西", "吕梁市", "中阳县暖泉镇", "300040", "2021-01-22", "'1376969568@qq.com")
                , new User("jame", "湖北", "武汉", "仙桃村", "300040", "2021-01-22", "'2376969568@qq.com")
                , new User("andy", "河南", "洛阳市", "河南", "300040", "2021-01-22", "'5376969568@qq.com"));


        for (Map.Entry<String, User> stringUserEntry : userMap.entrySet()) {
            objects.add(stringUserEntry.getValue());
        }

        return Result.ok(objects);
    }


    @PostMapping("/add")
    public Result addUser(@RequestBody User user,HttpServletRequest request) {
        System.out.println(request.getSession().getId());
        System.out.println(request.getSession().getAttribute("steven"));
        if (Optional.ofNullable(user).isPresent()) {
            String name = user.getName();
            userMap.put(name, user);
        }
        return Result.ok("添加成功");
    }


    @PostMapping("/addList")
    public Result addUserList(@RequestBody List<User> user) {
        for (User user1 : user) {
            if (null == user1.getName() || "".equals(user1.getName())) continue;
            userMap.put(user1.getName(), user1);
        }
        return Result.ok("添加成功" + userMap.size());
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
class User {

    private String name;
    private String province;
    private String city;
    private String address;
    private String zip;
    private String date;
    private String email;


}

class Result {
    private int code;

    private String message;

    private Object data;

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result ok(Object data) {
        return new Result(200, "success", data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
