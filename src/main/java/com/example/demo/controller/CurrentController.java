package com.example.demo.controller;

import com.example.demo.config.ExecutorsMonitor;
import com.example.demo.vo.MultipartFileParam;
import com.example.demo.vo.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Steven
 */
@RestController
public class CurrentController {

    @Resource(name = "executorsMonitor")
    private ExecutorsMonitor threadPoolExecutor;

    @PostMapping("/upload")
    public Response uploadFileVerify(@RequestParam("file") MultipartFile multipartFile,
                                     @RequestParam("attachmentFile") List<MultipartFile> multipartFileList, HttpServletRequest request, String name) throws InterruptedException {

        System.out.println(multipartFile.getOriginalFilename());
        System.out.println(request.getHeader("token"));
        System.out.println(name);
        if (CollectionUtils.isNotEmpty(multipartFileList)){
            for (MultipartFile file : multipartFileList) {
                System.out.println(file.getOriginalFilename());
            }
        }

        Random random = new Random();
        int i = random.nextInt(10);
        int time = i*1000;
        Thread.sleep(time);
        System.out.println("---------------------------------------------"+time);
        return Response.ok();
    }

    public static void main(String[] args) {
      HashMap<String,String> hashmap=  new HashMap<>();

      hashmap.put("L","");

    }
    @PostMapping("/second")
    public Result seconde(@RequestBody User user) {

        synchronized (this) {
            doSomething();
            doSomething2();
        }

        threadPoolExecutor.submit(() -> {
            doSomething3();
        });
        return Result.ok("成功");
    }


    private void doSomething() {
        System.out.println("doSomething");
    }

    private void doSomething2() {
        System.out.println("doSomething2");
    }

    private void doSomething3() {
        Random random = new Random();
        try {
            int i1 = random.nextInt(10) * 1000;
            Thread.sleep(i1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("doSomething3");
    }
}
