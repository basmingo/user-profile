package com.iprody.user.profile.e2e.stepdefs;

import io.cucumber.java.en.Then;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseStatusStepdefs {

    /**
     * Verifies that the response code matches the expected status code.
     *
     * @param expectedStatusCode the expected HTTP status code.
     */
    @Then("response code is {int}")
    public void responseCodeIs(int expectedStatusCode) {
        final int actualStatusCode = TestContext.CONTEXT.getResponse(ResponseEntity.class).getStatusCode().value();
        assertThat(actualStatusCode).isEqualTo(expectedStatusCode);
    }
}
