package com.iprody.user.profile.e2e.stepdefs.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprody.user.profile.e2e.RestResponseExceptionHandler;
import com.iprody.user.profile.e2e.generated.api.UserProfileApiApi;
import com.iprody.user.profile.e2e.stepdefs.TestContext;
import com.iprody.user.profile.e2e.stepdefs.DataTableMapper;
import io.cucumber.java.en.When;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Map;

/**
 * The type of create user stepdefs.
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CreateUserStepdefs {
    /**
     * An instance of the user profile API client.
     */
    private UserProfileApiApi userProfileApi;

    ObjectMapper objectMapper;

    /**
     * Sends a POST request to the user profile API to create a user.
     *
     * @param dataTable the user data.
     */
    @When("a client wants to create a user:")
    public void aClientWantToCreateUser(Map<String, String> dataTable) {
        final var userDto = DataTableMapper.mapToUserDto(dataTable);
        final var response = RestResponseExceptionHandler.sendRequest(
                () -> userProfileApi.createUserWithHttpInfo(userDto), objectMapper
        );

        TestContext.CONTEXT.setResponse(response);
    }
}
