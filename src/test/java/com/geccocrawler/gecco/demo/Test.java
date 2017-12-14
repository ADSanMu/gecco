package com.geccocrawler.gecco.demo;

import java.util.concurrent.CountDownLatch;

/**
 * @author a637182
 * @email Jiacheng_Deng@ssga.com
 * @date 12/7/2017
 */
public class Test {

    public static void main(String[] args) throws Exception {
        final CountDownLatch cld = new CountDownLatch(10);

        new Thread(() -> {
            for (int i = 1; i < 100; i++) {
                cld.countDown();
                System.out.println(i);
            }
            System.out.println("finish");
        }).start();

        cld.await();

        System.out.println("success");
    }
}
