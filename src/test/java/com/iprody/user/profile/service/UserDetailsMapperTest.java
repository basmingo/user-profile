package com.iprody.user.profile.service;

import com.iprody.user.profile.api.dto.UserDetailsDto;
import com.iprody.user.profile.persistence.entity.UserDetails;
import org.junit.jupiter.api.Test;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class UserDetailsMapperTest {
    private static final String TELEGRAM_ID = "TestTelegramId";
    private static final String MOBILE_PHONE = "TestMobilePhone";
    private static final long FIRST_ELEMENT_ID = 1;

    /**
     * Checks, if mapper takes DTO and
     * returns a correct Entity.
     */
    @Test
    void shouldReturnValidObject_WhenMaps() {
        final var userDetailsDto = getUserDetailsDto();
        final var expectedUserDetails = getUserDetails();
        final var mappedUserDto = new UserDetailsMapper().map(userDetailsDto);

        assertThat(mappedUserDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedUserDetails);
    }

    /**
     * Checks, if mapper takes Entity and
     * returns a correct Dto.
     */
    @Test
    void shouldReturnValidUser_WhenMapsUserDTOContainingUserDetailsDTO() {
        final var expectedUserDetailsDto = getUserDetailsDto();
        final var userDetails = getUserDetails();
        final var actualUserDetailsDto = new UserDetailsMapper().map(userDetails);

        assertThat(actualUserDetailsDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedUserDetailsDto);
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
