package com.iprody.user.profile.e2e.stepdefs.user;

import com.iprody.user.profile.e2e.RestResponseExceptionHandler;
import com.iprody.user.profile.e2e.generated.api.UserProfileApiApi;
import com.iprody.user.profile.e2e.stepdefs.TestContext;
import io.cucumber.java.en.When;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FindUserStepdefs {

    /**
     * An instance of the user profile API client.
     */
    UserProfileApiApi userProfileApi;

    /**
     * Sends a GET request to the user profile API to find a user by their ID.
     *
     * @param id the ID of the user to find
     */
    @When("a client wants to find a user with id: {int}")
    public void aClientWantsToFindAUserWithId(int id) {
        final ResponseEntity<?> response = RestResponseExceptionHandler.sendRequest(() ->
                userProfileApi.getUserWithDetailsWithHttpInfo((long) id)
        );
        TestContext.CONTEXT.setResponse(response);
    }

}
