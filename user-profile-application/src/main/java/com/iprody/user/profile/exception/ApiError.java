package com.iprody.user.profile.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * The class represents the error structure via the REST API.
 */
@Getter
@AllArgsConstructor
public class ApiError {
    /**
     * Status of the exception.
     */
    private final int status;
    /**
     * Message of the exception.
     */
    private final String message;
    /**
     * Details of the exception.
     */
    private final List<String> details;
}
