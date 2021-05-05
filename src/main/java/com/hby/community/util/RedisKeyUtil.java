package com.hby.community.util;

public class RedisKeyUtil {
    public static final String PREFIX_ENTITY_LIKE = "like:entity";
    public static final String PREFIX_USER_LIKE = "like:user";
    private static final String SPILT = ":";

    //某个实体的赞
    //like:entity:entityType:entityId->SET(userId)
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPILT + entityType + SPILT + entityId;
    }

    //某个用户的赞
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPILT + userId;
    }

}
