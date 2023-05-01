package com.iprody.user.profile.e2e.stepdefs.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprody.user.profile.e2e.RestResponseExceptionHandler;
import com.iprody.user.profile.e2e.generated.api.UserProfileApiApi;
import com.iprody.user.profile.e2e.stepdefs.DataTableMapper;
import com.iprody.user.profile.e2e.stepdefs.TestContext;
import io.cucumber.java.en.When;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UpdateUserStepdefs {

    UserProfileApiApi userProfileApi;

    ObjectMapper objectMapper;

    @When("a client wants to update a user with id <{long}> to:")
    public void aClientWantsToUpdateAUserWithId(long id, Map<String, String> dataTable) {
        final var userDto = DataTableMapper.mapToUserDto(dataTable);
        final ResponseEntity<?> response = RestResponseExceptionHandler.sendRequest(
                () -> userProfileApi.updateUserWithHttpInfo(id, userDto), objectMapper
        );
        TestContext.CONTEXT.setResponse(response);
    }
}
