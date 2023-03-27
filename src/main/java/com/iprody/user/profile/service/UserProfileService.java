package com.iprody.user.profile.service;

import com.iprody.user.profile.api.dto.UserDetailsDto;
import com.iprody.user.profile.api.dto.UserDto;
import liquibase.util.BooleanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Represents a class, with a set of operations on
 * User and UserDetails. <p>
 * Implements a Facade design pattern, retraces and map
 * objects on Services, which represent operations
 * on User and UserDetails.
 */
@Service
@RequiredArgsConstructor
public class UserProfileService {

    /**
     * Service, which contains a set of operations
     * on User entity.
     */
    private final UserService userService;

    /**
     * Mapper, which contains a set of operations
     * for mapping DTO and Entities.
     */
    private final UserMapper userMapper;

    /**
     * Create a user and return a Mono of the created user.
     *
     * @param userDto The user object that we want to create.
     * @return A Mono<UserDto>
     */
    public Mono<UserDto> createUser(UserDto userDto) {
        return userMapper.map(userDto)
                .flatMap(userService::createUser)
                .flatMap(userMapper::map);
    }

    /**
     * Update the user with the given id with the given userDto.
     *
     * @param id The id of the user to update.
     * @param userDto The user object that will be updated.
     * @return A Mono<UserDto>
     */
    public Mono<UserDto> updateUser(long id, UserDto userDto) {
        return userService.existsById(id)
                .filter(BooleanUtil::isTrue)
                .flatMap(ignore -> userMapper.map(userDto))
                .flatMap(userService::updateUser)
                .flatMap(userMapper::map);
    }

    /**
     * Update the user details for the given user id and user details id.
     *
     * @param userId The id of the user that owns the user details.
     * @param id The id of the user details to be updated.
     * @param userDetailsDto The user details object that will be updated.
     * @return A Mono<UserDetailsDto>
     */
    public Mono<UserDetailsDto> updateUserDetails(long userId, long id, UserDetailsDto userDetailsDto) {
        return null;
    }

    /**
     * Get a user with details.
     *
     * @param id The id of the user to retrieve
     * @return A Mono<UserDto>
     */
    public Mono<UserDto> getUserWithDetails(long id) {
        return userService.getUserWithDetails(id)
                .flatMap(userMapper::map);
    }

    /**
     * Get the user details for the user with the given id, and the given id.
     *
     * @param userId The userId of the user who is making the request.
     * @param id The id of the user to be updated.
     * @return A Mono<UserDetailsDto>
     */
    public Mono<UserDetailsDto> getUserDetails(long userId, long id) {
        return null;
    }
}
