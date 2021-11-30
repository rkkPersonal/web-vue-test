package com.example.demo;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class RandomAccessFileTest {


    public static void main(String[] args) throws Exception {

        RandomAccessFile randomAccessFile = new RandomAccessFile("a.txt","rw");



        System.out.println("file length ="+randomAccessFile.length());

        randomAccessFile.seek(0);


        randomAccessFile.writeBytes("sxr 2----");
        long filePointer = randomAccessFile.getFilePointer();

        System.out.println("filePoint ="+filePointer);
        byte[]  buff=new byte[1024];
        //用于保存实际读取的字节数
        int hasRead=0;
        //循环读取
        while((hasRead=randomAccessFile.read(buff))>0){
            //打印读取的内容,并将字节转为字符串输入
            System.out.println(new String(buff,0,hasRead));

        }
    }

}
