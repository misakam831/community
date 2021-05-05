package com.hby.community.controller;

import com.hby.community.entity.User;
import com.hby.community.service.LikeService;
import com.hby.community.util.CommunityUtil;
import com.hby.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId, int entityUserId) {
        User user = hostHolder.getUser();

        //点赞
        likeService.like(user.getId(), entityType, entityId, entityUserId);
        //数量
        long likeCount = likeService.findEntityLikeCount(entityType, entityId);
        //状态
        int status = likeService.findEntityLikeStatus(entityType, entityId, user.getId());

        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("status", status);
        return CommunityUtil.getJSONString(0, null, map);
    }
}
