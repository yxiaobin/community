package com.newcoder.community.util;

public class RedisKeyUtil {
    private static final String SPLIT=":";

    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static final String PREFIX_USER_LIKE = "like:user";
    private static final String PREFIX_FOLLOW = "followee";
    private static final String PREFIX_FOLLOWER = "follower";
    private static final String PREFIX_kAPTCHA = "kaptcha";
    private static final String PREFIX_TICKET = "ticket";
    private static final String PREFIX_USER = "user";


    //某个实体的赞
    //like:entity:entityType:entityId  -> set(userId)
    public static  String getEntityLikeKey(int entityType, int entityId){
        return  PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    //某个用户的赞
    //like:user:userid  -> int
    public static String getUserLikeKey(int userId){
        return PREFIX_USER_LIKE + SPLIT + userId;
    }

    //某个用户关注的
    //followee:userid:entityType -> zset(entityid,now date)
    public static String getFolloweeLikeKey(int userId, int entityType){
        return PREFIX_FOLLOW + SPLIT + userId + SPLIT + entityType;
    }

    //某个用户的粉丝
    //follower:entityType:entityId -> zset(userid,now date)
    public static String getFollowerLikeKey( int entityType, int entityId){
        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }

    //登录验证码
    public static  String getKaptchaKey(String owner){
        return PREFIX_kAPTCHA + SPLIT + owner;
    }

    //登录验证码
    public static  String getPrefixTicket(String ticket){
        return PREFIX_TICKET+ SPLIT + ticket;
    }

    public static String getUserKey(int userId){
        return PREFIX_USER + SPLIT + userId;
    }
}
