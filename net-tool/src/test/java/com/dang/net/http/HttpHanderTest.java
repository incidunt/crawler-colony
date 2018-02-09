package com.dang.net.http;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;


/**
 * Description:
 */

public class HttpHanderTest {
    private String url = "172.24.76.123/dang/select";
    private int count = 0;
    @Test
    public void header() throws Exception {
        String  res = HttpHander.hand().url(url).
                header("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:53.0) Gecko/20100101 Firefox/53.0")
                .header("Content-Length","100").doGet().content();
        System.out.println(res);
    }

    @Test
    public void doGet() throws Exception {
        int threadCount = 100;
        int count = 10000;

        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        long start = System.currentTimeMillis();
        final CountDownLatch endGate = new CountDownLatch(threadCount);
        for (int i = 0 ;i<threadCount;i++) {
             pool.execute(new Runnable() {
                 @Override
                 public void run() {
                     for(int i = 0; i < count; i++) {
                         try {
                             String res = HttpHander.hand().url(url).param("id", "1").param("name", "dang")
                                     .doGet().content();
                         } catch (IOException e) {
                             e.printStackTrace();
                         } finally {

                         }
                     }
                     endGate.countDown();
                 }
             });
        }
        endGate.await();
        pool.shutdown();
        System.out.println("time:" + (System.currentTimeMillis() - start));
        Thread.sleep(100000);
    }



    @Test
    public void doPost() throws Exception {
        String  res = HttpHander.hand().url(url).param("id","1").param("name","dang")
                .header("Connection", "close")
                .doPost().content();
        System.out.println(res);

    }


}
