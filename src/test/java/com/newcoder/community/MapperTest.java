package com.newcoder.community;

import com.newcoder.community.Dao.DiscussPostMapper;
import com.newcoder.community.Dao.LoginTickerMapper;
import com.newcoder.community.Dao.MessageMapper;
import com.newcoder.community.Dao.UserMapper;
import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.LoginTicket;
import com.newcoder.community.entity.Message;
import com.newcoder.community.entity.User;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Date;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class) //加载配置类
public class MapperTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void testUser(){
        User user = userMapper.selectById(1);
        System.out.println(user);

        user = userMapper.selectByName("张三");
        System.out.println(user);

        user=userMapper.selectByEmail("123456@qq.com");
        System.out.println(user);
    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("333333");
        user.setSalt("abc");
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());
        ;
        user.setType(1);
        int rows = userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());
    }
    @Test
    public void updataUser(){
        int rows = userMapper.updateStatus(101,100);
        System.out.println(rows);

        rows = userMapper.updateHeader(101,"http://www.nowcoder.com/202.png");
        System.out.println(rows);

        rows = userMapper.updatePassword(101,"hellopassword");
        System.out.println(rows);

    }

    @Autowired
    DiscussPostMapper discussPostMapper;
    @Test
    public void testSelectPosts(){
        List<DiscussPost>  list = discussPostMapper.selectDiscussPosts(149, 0, 10,0);
        for (DiscussPost d : list){
            System.out.println(d);
        }

        int rows = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(rows);
    }

    @Autowired
    LoginTickerMapper loginTickerMapper;

    @Test
    public void TestInsertLoginTicket(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000*60*10));

        loginTickerMapper.insertloginTicket(loginTicket);
    }
//    @Test
//    public void TestSelectLoginTicket(){
//        LoginTicket abc = loginTickerMapper.selectLoginTicketByTicket("abc");
//        System.out.println(abc);
//
//        loginTickerMapper.updateStatus("abc",1);
//        abc = loginTickerMapper.selectLoginTicketByTicket("abc");
//        System.out.println(abc);
//
//    }


    @Autowired
    private MessageMapper messageMapper;
    @Test
    public void testSelectLetters(){
        List<Message> list = messageMapper.selectConversations(111, 0, 20);
        for(Message message:list){
            System.out.println(message);
        }

        int count = messageMapper.selectConversationsCount(111);
        System.out.println(count);

        List<Message> messages = messageMapper.selectLetters("111_112",0,10);
        for(Message message:messages){
            System.out.println(message);
        }

        int count1 = messageMapper.selectLetterCount("111_112");
        System.out.println(count1);

        int count2 = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println(count2);
    }
}
