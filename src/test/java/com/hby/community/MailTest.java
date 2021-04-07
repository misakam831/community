package com.hby.community;

import com.hby.community.util.MainClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTest {
    @Autowired
    private MainClient mainClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail(){
        mainClient.sendMail("116215751@qq.com","Html","老婆老婆我爱你");
    }
    @Test
    public void testHtmlMail(){
        Context context=new Context();
        context.setVariable("username","sunday");

        String content= templateEngine.process("/mail/demo", context);
        System.out.println(content);
        mainClient.sendMail("1162157581@qq.com","Html",content);

    }

}
