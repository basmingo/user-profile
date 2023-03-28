package com.iprody.user.profile.e2e;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Class represents entry point for testing
 * SpringContext by cucumber.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserProfileE2eApplication.class)
public class SpringContextTest {
    /**
     * Test for checking SpringContext.
     */
    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
