package com.newcoder.community;

import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.service.DiscussPostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class) //加载配置类
public class CaffeinTest {

    @Autowired
    private DiscussPostService discussPostService;

    public void initDataFormat(){
        for(int i =0; i<=300000;i++){
            DiscussPost post = new DiscussPost();
            post.setUserId(111);
            post.setTitle("压力测试文章"+ i);
            post.setContent("我要进行压力测试");
            post.setCreateTime(new Date());
            post.setScore(Math.random()*2000);
            discussPostService.addDiscussPost(post);
            System.out.println("["+ i +"/300000]");
        }
    }

    public void testCache(){
        System.out.println(discussPostService.findDiscussPosts(0,0,10,1));
        System.out.println(discussPostService.findDiscussPosts(0,0,10,1));
        System.out.println(discussPostService.findDiscussPosts(0,0,10,1));
        System.out.println(discussPostService.findDiscussPosts(0,0,10,0));
    }
}
