package com.newcoder.community.Dao;

import com.newcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);
    int selectCountByEntity(int entityType, int entityId);
    //增加评论
    int addInsertComment(Comment comment);

    Comment selectCommentById(int id);
}
