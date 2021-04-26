package com.hby.community.service;

import com.hby.community.dao.DiscussPostMapper;
import com.hby.community.entity.DiscussPost;
import com.hby.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Autowired
    private SensitiveFilter sensitiveFilter;

    public List<DiscussPost> findDiscussPost(int userId,int offset,int limit){
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);

    }
    public int findDiscussPostsRows(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    public int addDisscussPost(DiscussPost post){
        if (post==null){
            throw new IllegalArgumentException("必须添加讨论帖");
        }

        //转义html
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        //过滤
        post.setTitle(sensitiveFilter.filter(post.getTitle()));
        post.setContent(sensitiveFilter.filter(post.getContent()));

        return discussPostMapper.insertDiscussPost(post);
    }

    public DiscussPost findDiscussPostById(int id) {
        return discussPostMapper.selectDiscussPostById(id);
    }

    public int updateCommentCount(int id, int commentcount) {
        return discussPostMapper.updateCommentCount(id, commentcount);
    }


}
