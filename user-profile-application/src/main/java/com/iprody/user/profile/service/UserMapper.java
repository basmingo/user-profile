package com.iprody.user.profile.service;

import com.iprody.user.profile.api.dto.UserDto;
import com.iprody.user.profile.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Represents a service, with a set of operations
 * for mapping DTO to the Entity, and Entity to DTO.
 */
@Service
@RequiredArgsConstructor
public class UserMapper {

    /**
     * Mapper to set UserDetails.
     */
    private final UserDetailsMapper userDetailsMapper;

    /**
     * @param userDto DTO object, which represents a User.
     * @return a User object, which could be persisted
     * by the Database.
     */
    public User map(UserDto userDto) {
        final var user = new User();

        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUserDetails(userDetailsMapper.map(userDto.getUserDetails()));

        return user;
    }

    /**
     * @param user an Entity object, that should be transformed
     *             to the DTO.
     * @return DTO, which represents a User.
     */
    public UserDto map(User user) {
        final var userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setUserDetails(userDetailsMapper.map(user.getUserDetails()));

        return userDto;
    }
}
