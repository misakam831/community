package com.hby.community;

import com.hby.community.dao.DiscussPostMapper;
import com.hby.community.dao.LoginTicketMapper;
import com.hby.community.dao.UserMapper;
import com.hby.community.entity.DiscussPost;
import com.hby.community.entity.LoginTicket;
import com.hby.community.entity.User;
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
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public  void  testselectById(){
        User user = userMapper.selectById(101);
        System.out.println(user);
        user=userMapper.selectByName("aaa");
        System.out.println(user);
    }
    @Test
    public void testinsertuser(){
        User user=new User();
        user.setUsername("test");
        user.setPassword("1111");
        user.setSalt("abc");
        user.setEmail("11@");
        user.setHeaderUrl("111");
        user.setCreateTime(new Date());

        int rows=userMapper.insertUser(user);


        System.out.println(rows);
        System.out.println(user.getId());
    }

    @Test
    public void testupdatePassword(){
        int rows=userMapper.updatePassword(151,"已修改");
        System.out.println(rows);

    }

    @Test
    public void testSelectPosts(){
        List<DiscussPost> list=discussPostMapper.selectDiscussPosts(0,0,10);
        for (DiscussPost p : list)
        {
            System.out.println(p);

        }

    }
    @Test
    public void testinsertLoginTicket(){
        LoginTicket loginTicket=new LoginTicket();
        loginTicket.setTicket("abc");
        loginTicket.setExpired(new Date(System.currentTimeMillis()+10*60*10));
        loginTicket.setStatus(0);
        loginTicket.setUserId(101);
        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testselectByTicket(){
        String ticket="abc";
        LoginTicket loginTicket = loginTicketMapper.selectByTicket(ticket);
        System.out.println(loginTicket);
        loginTicketMapper.updateStatus(ticket,1);
        LoginTicket loginTicket1 = loginTicketMapper.selectByTicket(ticket);
        System.out.println(loginTicket1);

    }
}
