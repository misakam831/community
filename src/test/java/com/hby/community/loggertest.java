package com.hby.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class loggertest {
    public static final Logger logger = LoggerFactory.getLogger(loggertest.class);

    @Test
    public void logger() {
        logger.debug("debuglog");
        logger.info("infolog");
        logger.warn("warnlog");
        logger.error("errorlog");
    }
}
