package com.example.demo.vo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Steven
 */
@Data
@ToString

public class MultipartFileParam {
    /**
     * 当前正在上传的分片序号
     */
    private int chunkNumber;

    /**
     * 分片之后每个文件的大小
     */
    private int chunkSize;

    /**
     * 当前分片的大小
     */
    private long currentChunkSize;

    /**
     * 总大小
     */
    private long totalSize;

    /**
     * 请求中包含的文件的唯一标识符。
     */
    private String identifier;

    /**
     * 原文件名
     */
    private String filename;

    /**
     * 选择目录时文件的相对路径
     */
    private String relativePath;

    /**
     * 总共有多少个分片
     */
    private long totalChunks;

    /**
     * 当前分片流文件
     */
    private MultipartFile file;
}

