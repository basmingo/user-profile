package com.iprody.user.profile.e2e.stepdefs.user;

import com.iprody.user.profile.e2e.generated.model.ApiError;
import com.iprody.user.profile.e2e.generated.model.UserDto;
import com.iprody.user.profile.e2e.stepdefs.DataTableMapper;
import com.iprody.user.profile.e2e.stepdefs.TestContext;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ResponseStepdefs {

    /**
     * Verifies that the response body matches the expected response body.
     *
     * @param expectedResponseBody the expected response body in the form of a Map.
     */
    @Then("response body contains:")
    public void responseBodyContains(Map<String, String> expectedResponseBody) {
        final var expected = DataTableMapper.mapToUserDto(expectedResponseBody);
        final var actualResponse = TestContext.CONTEXT.getResponse(ResponseEntity.class);

        assertThat(actualResponse)
                .extracting(response -> (UserDto) response.getBody())
                .isEqualTo(expected);
    }

    /**
     * Verifies that the response body contains an expected error.
     *
     * @param expectedError the expected error in the form of a Map.
     */
    @Then("response body contains error:")
    public void responseBodyContainsError(Map<String, String> expectedError) {
        final var expectedResponse = DataTableMapper.mapToApiError(expectedError);
        final var actualResponse = TestContext.CONTEXT.getResponse(ResponseEntity.class);
        final var actualApiError = (ApiError) actualResponse.getBody();

        final SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualResponse).isNotNull();
        softly.assertThat(actualApiError.getStatus()).isEqualTo(expectedResponse.getStatus());
        softly.assertThat(actualApiError.getMessage()).startsWith(expectedResponse.getMessage());
        softly.assertAll();
    }
}
