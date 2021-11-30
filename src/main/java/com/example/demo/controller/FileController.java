package com.example.demo.controller;

import com.example.demo.service.UploadService;
import com.example.demo.vo.MultipartFileParam;
import com.example.demo.vo.Response;
import com.example.demo.vo.upload.Chunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @author Steven
 */
@RestController
@RequestMapping(value = "/file", produces = {"application/json;utf-8"})
public class FileController {

    public static final String SINGLE_FOLDER = "single_file";

    @Resource
    private UploadService uploadService;

    @GetMapping("/upload")
    public Response uploadFileVerify(MultipartFileParam file,HttpServletResponse response,HttpServletRequest request) {
        return uploadService.uploadFileVerify(file,response);
    }

    @PostMapping("/upload")
    public Response breakpointUpload(MultipartFileParam file,HttpServletResponse response,HttpServletRequest request) throws IOException {
        Cookie cookie = new Cookie("user-info", UUID.randomUUID().toString().replace("-", ""));
        cookie.setMaxAge(30*60);
        cookie.setPath("/");
        response.addCookie(cookie);
        return uploadService.breakPointUploadFile(file);
    }


    @GetMapping("/merge")
    public Response fileMerge(MultipartFileParam file,HttpServletResponse response,HttpServletRequest request) throws IOException {
        return uploadService.fileMerge("E:\\my_demo_test\\break_point_upload", "E:\\my_demo_test\\centos.exe");
    }


    @PostMapping("/single")
    public void singleUpload(Chunk chunk) {
        // 获取传来的文件
        MultipartFile file = chunk.getFile();
        // 获取文件名
        String filename = chunk.getFilename();
        try {
            // 获取文件的内容
            byte[] bytes = file.getBytes();
            // SINGLE_UPLOADER是我定义的一个路径常量，这里的意思是，如果不存在该目录，则去创建
            if (!Files.isWritable(Paths.get(SINGLE_FOLDER))) {
                Files.createDirectories(Paths.get(SINGLE_FOLDER));
            }
            // 获取上传文件的路径
            Path path = Paths.get(SINGLE_FOLDER, filename);
            // 将字节写入该文件
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @PostMapping(value = "/chunk", produces = "multipart/form-data")
    public Map<String, Object> saveChunk(Chunk chunk) {
        // 这里的操作和保存单段落的基本是一致的~
        MultipartFile file = chunk.getFile();
        Integer chunkNumber = chunk.getChunkNumber();
        String identifier = chunk.getIdentifier();
        return new HashMap<>();
    }


    @GetMapping("/download")
    public void breakpointDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        InputStream in = null;
        OutputStream out = null;
        File file = new File("ideaIU-2020.2.1.exe");
        in = new FileInputStream(file);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/x-download");
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(file.getName().getBytes(Charset.defaultCharset()), "iso-8859-1"));

        long pos = 0;
        if (null != request.getHeader("Range")) {
            // 断点续传
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            try {
                pos = Long.parseLong(request.getHeader("Range").replaceAll("bytes=", "").replaceAll("-", ""));
            } catch (NumberFormatException e) {
                pos = 0;
            }
        }
        out = response.getOutputStream();
        String contentRange = new StringBuffer("bytes ").append(pos + "").append("-").append((file.length() - 1) + "").append("/").append(file.length() - 1 + "").toString();
        response.setHeader("Content-Range", contentRange);
        in.skip(pos);
        byte[] buffer = new byte[1024 * 10];
        int length = 0;
        while ((length = in.read(buffer, 0, buffer.length)) != -1) {
            out.write(buffer, 0, length);
            // Thread.sleep(100);
        }

    }
}
