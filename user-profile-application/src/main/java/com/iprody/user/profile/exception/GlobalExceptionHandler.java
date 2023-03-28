package com.iprody.user.profile.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * It takes a WebExchangeBindException, which is a Spring exception that is thrown when a request body fails
     * validation, and returns a Mono of an ApiError, which is a custom exception that contains a status code, a
     * message, and a list of details.
     *
     * @param e The exception object
     * @return A Mono of ApiError
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ApiError> handleValidationExceptions(WebExchangeBindException e) {
        final List<String> details = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        final ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                details
        );

        return Mono.just(apiError);
    }

    /**
     * It handles the ResourceNotFoundException and returns a Mono of ApiError.
     *
     * @param e The exception that was thrown.
     * @return A Mono of ApiError
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ApiError> handleResourceNotFoundException(ResourceNotFoundException e) {
        final ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                Collections.emptyList()
        );

        return Mono.just(apiError);
    }

    /**
     * > This function handles the ResourceProcessingException and returns a Mono of ApiError.
     *
     * @param e The exception that was thrown.
     * @return A Mono of ApiError
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResourceProcessingException.class)
    public Mono<ApiError> handleResourceProcessingException(ResourceProcessingException e) {
        final ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(),
                Collections.emptyList()
        );

        return Mono.just(apiError);
    }
}
