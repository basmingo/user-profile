package com.iprody.user.profile.api.controller;

import com.iprody.user.profile.api.dto.UserDetailsDto;
import com.iprody.user.profile.api.dto.UserDto;
import com.iprody.user.profile.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * It's a controller class that exposes the endpoints for the user profile management.
 */
@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
@Tag(name = "User profile Api", description = "User profile management")
public class UserProfileController {

    /**
     * It's a constructor injection.
     */
    private final UserProfileService userProfileService;

    /**
     * Create user.
     *
     * @param userDto The request body.
     * @return A Mono of UserDto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created user"),
            @ApiResponse(responseCode = "400", description = "Missing or invalid request body"),
            @ApiResponse(responseCode = "500", description = "Internal error")})
    public Mono<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return userProfileService.createUser(userDto);
    }

    /**
     * Update user by Id.
     *
     * @param id      The id of the user to update
     * @param userDto The user object that will be updated.
     * @return A Mono of UserDto
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update user by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user"),
            @ApiResponse(responseCode = "400", description = "Missing or invalid request body"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal error")})
    public Mono<UserDto> updateUser(@PathVariable("id") long id,
                                    @Valid @RequestBody UserDto userDto) {
        return userProfileService.updateUser(id, userDto);
    }

    /**
     * Update user details by Id.
     *
     * @param userId         The user id of the user whose details we want to update.
     * @param id             The id of the user details to be updated.
     * @param userDetailsDto The request body.
     * @return A Mono of UserDetailsDto
     */
    @PutMapping("/{userId}/details/{id}")
    @Operation(summary = "Update user details by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user details"),
            @ApiResponse(responseCode = "400", description = "Missing or invalid request body"),
            @ApiResponse(responseCode = "404", description = "User details not found"),
            @ApiResponse(responseCode = "500", description = "Internal error")})
    public Mono<UserDetailsDto> updateUserDetails(@PathVariable("userId") long userId,
                                                  @PathVariable("id") long id,
                                                  @Valid @RequestBody UserDetailsDto userDetailsDto) {
        return userProfileService.updateUserDetails(userId, id, userDetailsDto);
    }

    /**
     * Get user by Id.
     *
     * @param id The id of the user to be retrieved.
     * @return A Mono of UserDto
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get user by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Schema not found"),
            @ApiResponse(responseCode = "500", description = "Internal error")})
    public Mono<UserDto> getUserWithDetails(@PathVariable("id") long id) {
        return userProfileService.getUserWithDetails(id);
    }

    /**
     * Get user details by Id.
     *
     * @param userId The userId is the id of the user whose details we want to get.
     * @param id     The id of the user
     * @return A Mono of UserDetailsDto
     */
    @GetMapping("{userId}/details/{id}")
    @Operation(summary = "Get user details by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Schema not found"),
            @ApiResponse(responseCode = "500", description = "Internal error")})
    public Mono<UserDetailsDto> getUserDetails(@PathVariable("userId") long userId,
                                               @PathVariable("id") long id) {
        return userProfileService.getUserDetails(userId, id);
    }
}
