package com.iprody.user.profile.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for cucumber context.
 */
@CucumberContextConfiguration
@SpringBootTest(
        classes = {UserProfileE2eApplication.class, RestTemplate.class},
        webEnvironment = WebEnvironment.RANDOM_PORT
)
public class CucumberSpringContextConfiguration {
    /**
     * Initialization of logger for logging test cases.
     */
    private final Logger log = LoggerFactory.getLogger(CucumberSpringContextConfiguration.class);

    /**
     * Need this method so the cucumber will recognize this class as glue and load spring context configuration.
     */
    @Before
    public void setUp() {
        log.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
    }
}
