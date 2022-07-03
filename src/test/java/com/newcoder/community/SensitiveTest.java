package com.newcoder.community;

import com.newcoder.community.util.SensitiveFilter;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class) //加载配置类
public class SensitiveTest {
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Test
    public void testSensivieFilter(){
        String text = "⭐⭐⭐⭐这***里**可以赌**博，可以嫖**娼。可以吸**毒，可以开⭐票⭐⭐⭐⭐⭐";
        String filter = sensitiveFilter.filter(text);
        System.out.println(filter);
    }

}
