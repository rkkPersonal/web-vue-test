package com.example.demo.service.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.CloudPageBlob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.UploadService;
import com.example.demo.vo.MultipartFileParam;
import com.example.demo.vo.Response;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Steven
 */
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    private static final Map<String, MultipartFileParam> catchMap = new ConcurrentHashMap<>();


    /**
     * 校验文件
     *
     * @param file
     * @return
     */
    @Override
    public Response uploadFileVerify(MultipartFileParam file, HttpServletResponse response) {
        if (catchMap.containsKey(file.getChunkNumber() + file.getIdentifier())) {
            response.setStatus(HttpStatus.PAYLOAD_TOO_LARGE.value());
            return Response.error("失败");
        }


        return Response.ok();
    }

    /**
     * 断点上传文件
     *
     * @param file
     * @return
     */
    @Override
    public Response breakPointUploadFile(MultipartFileParam file) throws IOException {
        int chunkNumber = file.getChunkNumber();
        int chunkSize = file.getChunkSize();
        long currentChunkSize = file.getCurrentChunkSize();
        long totalSize = file.getTotalSize();
        String identifier = file.getIdentifier();
        String filename = file.getFilename();
        String relativePath = file.getRelativePath();
        long totalChunks = file.getTotalChunks();
        MultipartFile file1 = file.getFile();

        File files = new File("E:\\my_demo_test\\breakpoint_upload");
        if (!files.exists()) {
            files.mkdirs();
        }
        String substring = filename.substring(filename.lastIndexOf("."), filename.length());
        File fileName = new File(files + "/" + chunkNumber + substring);
        if (!fileName.exists()) {
            fileName.createNewFile();
        }

        file1.transferTo(fileName);

        //如果上传成功则，记录下来
        catchMap.put(file.getChunkNumber() + file.getIdentifier(), file);
        return Response.ok();
    }

    @Override
    public Response fileMerge(String tempPath, String targetFileName) throws IOException {
        File files = new File(tempPath);
        File[] allFiles = files.listFiles();
        int length = allFiles.length;
        File newFile = new File(targetFileName);
        if (!newFile.exists()) {
            newFile.createNewFile();
        }

        // 文件排序
        Map<Integer, File> fileMap = new HashMap<>();
        List<Integer> fileSortList = new ArrayList<>();
        for (File file : allFiles) {
            String name = file.getName();
            String substring = name.substring(0, name.lastIndexOf("."));
            fileSortList.add(Integer.valueOf(substring));
            fileMap.put(Integer.valueOf(substring), file);
        }

        fileSortList = fileSortList.stream().sorted().collect(Collectors.toList());

        // 开始合并文件
        long seek = 0;
        boolean flag = false;
        for (Integer fileName : fileSortList) {
            File file = fileMap.get(fileName);
            RandomAccessFile rw = new RandomAccessFile(targetFileName, "rw");
            if (!flag) {
                rw.setLength(length);
                flag = true;
            }
            rw.seek(seek);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[fileInputStream.available()];
            while (fileInputStream.read(buffer) > -1) {
                rw.write(buffer);
            }
            seek = rw.getFilePointer();
        }

        return Response.ok();
    }


    public static void main(String[] args) throws IOException {


        new UploadServiceImpl().fileMerge("E:\\my_demo_test\\break_point_upload", "E:\\my_demo_test\\centos.exe");
    }


}
