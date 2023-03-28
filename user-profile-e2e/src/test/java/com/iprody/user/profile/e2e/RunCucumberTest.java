package com.iprody.user.profile.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Class represents entry point into cucumber testing
 * and basic configuration for reports, features etc.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-reports/cucumber-report.html"},
        features = "src/test/resources/features")
public class RunCucumberTest {
}
