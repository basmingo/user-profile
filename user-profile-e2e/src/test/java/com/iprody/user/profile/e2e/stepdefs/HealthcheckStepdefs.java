package com.iprody.user.profile.e2e.stepdefs;

import com.iprody.user.profile.e2e.generated.api.ActuatorApi;
import com.iprody.user.profile.e2e.generated.model.HealthResponse;
import com.iprody.user.profile.e2e.generated.model.MappingResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.SoftAssertions;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Step definitions for the health check feature.
 * This class contains methods to check the health status of the UP Service
 * <p>
 * and verify that the User Endpoint is available.
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class HealthcheckStepdefs {

    /**
     * The Actuator API object used for making HTTP requests to the /health and /mappings endpoints.
     */
    ActuatorApi actuatorApi;

    /**
     * Given UP Service is up and running, this method checks the health status of the service by making a
     * call to the /health endpoint.
     * <p>
     * It verifies that the status code is 200 and that the health response contains a status of "UP".
     */
    @Given("UP Service is up and running")
    public void upServiceIsUpAndRunning() {
        final SoftAssertions softly = new SoftAssertions();
        final ResponseEntity<HealthResponse> response = actuatorApi.healthWithHttpInfo();
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        final var healthResponse = response.getBody();
        softly.assertThat(healthResponse).isNotNull();
        softly.assertThat(healthResponse.getStatus()).isEqualTo(Status.UP.toString());
        softly.assertAll();
    }

    /**
     * This method checks if the User Endpoint is available by making a call to the /mappings endpoint
     * and verifying that
     * <p>
     * the expected method and pattern are present in the response.
     *
     * @param expectedUserEndpoint a map containing the expected HTTP method and pattern
     */
    @And("User Endpoint is available:")
    public void userEndpointIsAvailable(Map<String, String> expectedUserEndpoint) {
        final SoftAssertions softly = new SoftAssertions();
        final ResponseEntity<MappingResponse> mappingResponseResponseEntity = actuatorApi.mappingsWithHttpInfo();
        softly.assertThat(mappingResponseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        final var mappingResponse = mappingResponseResponseEntity.getBody();
        softly.assertThat(mappingResponse).isNotNull();
        softly.assertThat(
                        EndpointFinder.endpointIsAvailable(
                                mappingResponse,
                                expectedUserEndpoint.get("method"),
                                expectedUserEndpoint.get("endpoint")
                        )
                )
                .isTrue();
        softly.assertAll();
    }
}
