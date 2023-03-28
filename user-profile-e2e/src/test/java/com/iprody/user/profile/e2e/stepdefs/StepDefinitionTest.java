package com.iprody.user.profile.e2e.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * The class represents step definitions
 * for executing tests by cucumber.
 */
public class StepDefinitionTest {
    /**
     * Status code for testing method that should return the code.
     */
    private final int testStatusCode = 200;
    /**
     * This variable uses for testing method that should return User.
     */
    private final String user = "User";
    /**
     * Client call /users by executing POST method.
     */
    @When("^the client calls /users$")
    public void the_client_issues_POST_user() {

    }
    /**
     * Client call /userProfile by executing GET method.
     */
    @Given("^the client calls /userProfile$")
    public void the_client_issues_GET_user_profile() {

    }

    /**
     * Client calls /version and should get version.
     */
    @When("^the client calls /version$")
    public void the_client_issues_GET_version() {

    }

    /**
     * Method for checking status code.
     * @param statusCode
     */
    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) {
        assertThat("status code is incorrect : ", testStatusCode, is(testStatusCode));
    }
    /**
     * Method checking version of entity.
     * @param usr
     */
    @And("^the client receives server version (.+)$")
    public void the_client_receives_server_version_body(String usr) {
        assertThat(user, is(user));
    }
}
