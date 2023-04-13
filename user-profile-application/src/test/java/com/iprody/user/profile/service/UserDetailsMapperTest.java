package com.iprody.user.profile.service;

import com.iprody.user.profile.api.dto.UserDetailsDto;
import com.iprody.user.profile.persistence.entity.UserDetails;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDetailsMapperTest {
    private static final String TELEGRAM_ID = "@TestUser";
    private static final String MOBILE_PHONE = "+ 111 222 333 44";
    private static final long FIRST_ELEMENT_ID = 1;

    /**
     * Creating UserDetails test data.
     *
     * @return userDetails
     */
    private UserDetails mapperUserDetails() {
        final var userDetails = new UserDetails();
        userDetails.setId(FIRST_ELEMENT_ID);
        userDetails.setTelegramId(TELEGRAM_ID);
        userDetails.setMobilePhone(MOBILE_PHONE);
        userDetails.setZoneId(ZoneId.systemDefault());

        return userDetails;
    }

    /**
     * Creating UserDetailsDto test data.
     *
     * @return userDetails
     */
    private UserDetailsDto mapperUserDetailsDto() {
        final var userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(FIRST_ELEMENT_ID);
        userDetailsDto.setTelegramId(TELEGRAM_ID);
        userDetailsDto.setMobilePhone(MOBILE_PHONE);
        userDetailsDto.setZoneId(ZoneId.systemDefault());

        return userDetailsDto;
    }

    /**
     * Checks, if mapper takes Entity With Details
     * and returns a correct Mono with DTO.
     */
    @Test
    void shouldReturnValidUserDetails_WhenMapperContainingUserDetailsDto() {
        final var expectedMapperUserDetailsDto = mapperUserDetailsDto();
        final var actualMappedUserDetailsDto = new UserDetailsMapper().map(mapperUserDetails());

        StepVerifier
                .create(actualMappedUserDetailsDto)
                .assertNext(userDetailsDtoCurrent -> assertThat(userDetailsDtoCurrent)
                        .isEqualTo(expectedMapperUserDetailsDto))
                .verifyComplete();
    }

    /**
     * Checks, if mapper takes Entity With Details
     * and returns an incorrect Mono with DTO.
     */
    @Test
    void shouldReturnNotValidUserDetails_WhenMapperContainingIncorrectUserDetailsDto() {
        final var expectedMapperUserDetails = mapperUserDetails();
        final var actualMappedUserDetailsDto = new UserDetailsMapper().map(mapperUserDetailsDto());

        StepVerifier
                .create(actualMappedUserDetailsDto)
                .assertNext(userDetailsDtoCurrent -> assertThat(userDetailsDtoCurrent)
                        .isNotEqualTo(expectedMapperUserDetails))
                .verifyComplete();
    }
}
