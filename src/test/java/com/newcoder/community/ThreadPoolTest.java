package com.newcoder.community;

import com.newcoder.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class) //加载配置类
public class ThreadPoolTest {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolTest.class);

    //JDK普通线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    //JDK可定时执行任务的线程池
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    //spring带的线程池

    //spring 普通
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    //spring 定时任务
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private AlphaService alphaService;


    private void sleep(long m){
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //1.jdk普通线程池
    @Test
    public void testExecutorService(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("hello,executor service");
            }
        };
        for(int i=0;i<10;i++){
            executorService.submit(task);

        }
        sleep(10000);
    }
    //2.JDK定时任务线性池
    @Test
    public void testScheduledExecutorService(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("hello, ScheduledExecutorService");
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(task,10000,1000, TimeUnit.MILLISECONDS);
        sleep(30000);
    }

    //3.spring的线程池
    @Test
    public void testSpringExecutorService(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("hello,ThreadPoolTaskExecutor");
            }
        };
        for(int i=0;i<10;i++){
            taskExecutor.submit(task);
        }
        sleep(10000);
    }
    //4.JDK定时任务线性池
    @Test
    public void testSpringScheduledExecutorService(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                logger.debug("hello, ThreadPoolTaskScheduler");
            }
        };
        Date startTime = new Date(System.currentTimeMillis()+10000);
        taskScheduler.scheduleAtFixedRate(task,startTime,1000);
        sleep(30000);
    }

    //5.注解运行多线程
    @Test
    public void testSpringExecutorServiceSimple(){
        for(int i=0;i<10;i++){
            alphaService.execute1();
        }
        sleep(10000);
    }
    @Test
    public void testSpringScheduledExecutorServiceSimple(){
        sleep(30000);
    }


}
