package com.example.demo.test;


import com.example.demo.utils.DownUtil;

/**
 * Created by amosli on 14-7-2.
 */
public class DownUtilTest {

    public static void main(String args[]) throws Exception {
        final DownUtil downUtil = new DownUtil("https://dlcdn.apache.org/activemq/KEYS", "1.txt", 100);

        downUtil.download();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(downUtil.getCompleteRate()<1){
                    System.out.println("已完成:"+downUtil.getCompleteRate());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

}
