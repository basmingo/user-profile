package com.iprody.user.profile.service;

import com.iprody.user.profile.api.dto.UserDetailsDto;
import com.iprody.user.profile.api.dto.UserDto;
import com.iprody.user.profile.persistence.entity.User;
import com.iprody.user.profile.persistence.entity.UserDetails;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.time.ZoneId;

/**
 * Unit test for checking if mapper works properly.
 */
class UserMapperTest {
    private static final String FIRST_NAME = "TestFirstName";
    private static final String LAST_NAME = "TestLastName";
    private static final String TELEGRAM_ID = "TestTelegramId";
    private static final String MOBILE_PHONE = "TestMobilePhone";
    private static final String EMAIL_ADDRESS = "TestEmail@gmail.com";
    private static final long FIRST_ELEMENT_ID = 1;
    private final UserMapper userMapper = new UserMapper(new UserDetailsMapper());

    /**
     * Checks, if mapper takes Entity With Details
     * and returns a correct Mono with DTO.
     */
    @Test
    void shouldReturnValidUserDTO_WhenMapsUserContainingUserDetails() {
        final var user = getUser();
        final var expectedUserDto = getUserDto();
        final var actualUserDto = userMapper.map(user);

        StepVerifier
                .create(actualUserDto)
                .expectNext(expectedUserDto)
                .verifyComplete();
    }

    /**
     * Checks, if mapper takes Dto with details
     * and returns a correct Mono with Entity.
     */
    @Test
    void shouldReturnValidUser_WhenMapsUserDTOContainingUserDetailsDTO() {
        final var expectedUser = getUser();
        final var userDto = getUserDto();
        final var actualUser = userMapper.map(userDto);

        StepVerifier
                .create(actualUser)
                .assertNext(user -> Assertions.assertThat(user)
                        .usingRecursiveComparison()
                        .isEqualTo(expectedUser))
                .verifyComplete();
    }

    private User getUser() {
        final var user = new User();
        user.setId(FIRST_ELEMENT_ID);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setEmail(EMAIL_ADDRESS);
        user.setUserDetails(getUserDetails());
        return user;
    }

    private UserDto getUserDto() {
        final var userDto = new UserDto();
        userDto.setId(FIRST_ELEMENT_ID);
        userDto.setFirstName(FIRST_NAME);
        userDto.setLastName(LAST_NAME);
        userDto.setEmail(EMAIL_ADDRESS);
        userDto.setUserDetails(getUserDetailsDto());
        return userDto;
    }

    private UserDetails getUserDetails() {
        final var userDetails = new UserDetails();
        userDetails.setId(FIRST_ELEMENT_ID);
        userDetails.setTelegramId(TELEGRAM_ID);
        userDetails.setMobilePhone(MOBILE_PHONE);
        userDetails.setZoneId(ZoneId.systemDefault());
        return userDetails;
    }

    private UserDetailsDto getUserDetailsDto() {
        final var userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(FIRST_ELEMENT_ID);
        userDetailsDto.setTelegramId(TELEGRAM_ID);
        userDetailsDto.setMobilePhone(MOBILE_PHONE);
        userDetailsDto.setZoneId(ZoneId.systemDefault());
        return userDetailsDto;
    }
}
