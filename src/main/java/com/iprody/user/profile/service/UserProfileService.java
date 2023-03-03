package com.iprody.user.profile.service;

import com.iprody.user.profile.api.dto.UserDetailsDto;
import com.iprody.user.profile.api.dto.UserDto;
import reactor.core.publisher.Mono;

public interface UserProfileService {
    /**
     * Create a user and return a Mono of the created user.
     *
     * @param userDto The user object that we want to create.
     * @return A Mono<UserDto>
     */
    Mono<UserDto> createUser(UserDto userDto);

    /**
     * Update the user with the given id with the given userDto.
     *
     * @param id The id of the user to update.
     * @param userDto The user object that will be updated.
     * @return A Mono<UserDto>
     */
    Mono<UserDto> updateUser(long id, UserDto userDto);

    /**
     * Update the user details for the given user id and user details id.
     *
     * @param userId The id of the user that owns the user details.
     * @param id The id of the user details to be updated.
     * @param userDetailsDto The user details object that will be updated.
     * @return A Mono<UserDetailsDto>
     */
    Mono<UserDetailsDto> updateUserDetails(long userId, long id, UserDetailsDto userDetailsDto);

    /**
     * Get a user with details.
     *
     * @param id The id of the user to retrieve
     * @return A Mono<UserDto>
     */
    Mono<UserDto> getUserWithDetails(long id);

    /**
     * Get the user details for the user with the given id, and the given id.
     *
     * @param userId The userId of the user who is making the request.
     * @param id The id of the user to be updated.
     * @return A Mono<UserDetailsDto>
     */
    Mono<UserDetailsDto> getUserDetails(long userId, long id);
}
