package com.iprody.user.profile.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprody.user.profile.e2e.generated.model.ApiError;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;

import java.util.function.Supplier;

/**
 * Rest response exception handler.
 */
@UtilityClass
public class RestResponseExceptionHandler {

    /**
     * Send request response entity and catch the exception.
     *
     * @param request the request
     * @return the response entity
     */
    @SneakyThrows
    public ResponseEntity<?> sendRequest(Supplier<ResponseEntity<?>> request, ObjectMapper objectMapper) {
        ResponseEntity<?> response;
        try {
            response = request.get();
        } catch (RestClientResponseException e) {
            final var apiError = objectMapper.readValue(e.getResponseBodyAsString(), ApiError.class);
            response = ResponseEntity.status(e.getStatusCode()).body(apiError);
        }
        return response;
    }
}
