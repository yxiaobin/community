package com.newcoder.community.service;

import com.newcoder.community.Dao.AlphaDao;
import com.newcoder.community.Dao.DiscussPostMapper;
import com.newcoder.community.Dao.UserMapper;
import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.User;
import com.newcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

@Service
@Scope("prototype") //默认是 sington, prototype每次创建bean都会创建一个实例。web项目中大部分都是使用单例的
public class AlphaService {
    @Autowired
    private AlphaDao alphaDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public  AlphaService(){
//        System.out.println("实例化");
    }
    @PostConstruct
    public void init(){
//        System.out.println("初始化");
    }
    @PreDestroy
    public void destory(){
//        System.out.println("销毁");
    }

    public String find(){
        return alphaDao.Select();
    }



    //用户注册+新人报到
    //通过注解实现事务
    /*
    * REQUIRED：支持当前事务（外部事物），A调用B，如果不存在则创建新事物
    * REQUIRED_NEW 创建一个新的事务，并且暂停外部事物 A调用B，B无视A的事务，自己创建新的事务
    * NESTED 如果当前存在外部事物，则嵌套在该事务中执行（嵌在A中执行，但是有独立的提交和回滚）
    * */

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Object save1(){
        //增加用户
        User user = new User();
        user.setUsername("alpha");
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5("123" + user.getSalt()));
        user.setEmail("alphpa@qq.com");
        user.setHeaderUrl("http://images.nowcoder.com/head/99t.png");
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        //增加帖子
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle("事务测试标题");
        post.setContent("新人报到");
        post.setCreateTime(new Date());
        discussPostMapper.insertDiscussPost(post);

        //后续逻辑发生报错
//        Integer.valueOf("abc");
        return "ok";
    }


    public Object save2(){
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {

                //增加用户
                User user = new User();
                user.setUsername("beta");
                user.setSalt(CommunityUtil.generateUUID().substring(0,5));
                user.setPassword(CommunityUtil.md5("123" + user.getSalt()));
                user.setEmail("beta@qq.com");
                user.setHeaderUrl("http://images.nowcoder.com/head/999t.png");
                user.setCreateTime(new Date());
                userMapper.insertUser(user);

                //增加帖子
                DiscussPost post = new DiscussPost();
                post.setUserId(user.getId());
                post.setTitle("事务测试标题2");
                post.setContent("新人报到2");
                post.setCreateTime(new Date());
                discussPostMapper.insertDiscussPost(post);

                //后续逻辑发生报错
//                Integer.valueOf("abc");
                return "ok";
            }
        });
    }

}
