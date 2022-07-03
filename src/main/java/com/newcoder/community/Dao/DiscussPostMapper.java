package com.newcoder.community.Dao;

import com.newcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(int userId,  int offset, int limit);

    //@Param 用于给参数起别名，如果该方法只有一个参数，并且在动态SQL中使用就必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);
    //增加帖子
    int insertDiscussPost(DiscussPost discussPost);

    //查看帖子的详情
    DiscussPost selectDiscussPostById(int id);

    int updateCommentCount(int id, int commentCount);
}
