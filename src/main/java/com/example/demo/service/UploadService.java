package com.example.demo.service;

import com.example.demo.vo.MultipartFileParam;
import com.example.demo.vo.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Steven
 */
public interface UploadService {

    /**
     * 断点上传文件
     *
     * @param file
     * @return
     */
    public Response breakPointUploadFile(MultipartFileParam file) throws IOException;


    public Response uploadFileVerify(MultipartFileParam file, HttpServletResponse response);


    public Response fileMerge(String tempPath, String targetFileName) throws IOException;
}
