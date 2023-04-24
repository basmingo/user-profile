package com.iprody.user.profile.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User details mapper test.
 */
public class UserDetailsMapperTest {

    /**
     * Checks, if mapper takes Entity With DetailsDto
     * and returns a correct Details.
     */
    @Test
    void shouldReturnValidUserDetailsDto_WhenMapperContainingUserDetails() {
        final var expectedUserDetailsDto = EntityHatchery.getUserDetailsDto();
        final var mappedUserDetailsDto = new UserDetailsMapper().map(EntityHatchery.getUserDetails());

        assertThat(mappedUserDetailsDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedUserDetailsDto);
    }

    /**
     * Checks, if mapper takes Entity With Details
     *      * and returns a correct DetailsDTO.
     */
    @Test
    void shouldReturnValidUserDetails_WhenMapperContainingCorrectUserDetailsDto() {
        final var expectedUserDetails = EntityHatchery.getUserDetails();
        final var mappedUserDetailsDto = new UserDetailsMapper().map(EntityHatchery.getUserDetailsDto());

        assertThat(mappedUserDetailsDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedUserDetails);
    }
}
