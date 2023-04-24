package com.iprody.user.profile.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for checking if mapper works properly.
 */
class UserMapperTest {

    private final UserMapper userMapper = new UserMapper(new UserDetailsMapper());

    /**
     * Checks, if mapper takes Entity With Details
     * and returns a correct Mono with DTO.
     */
    @Test
    void shouldReturnValidUserDTO_WhenMapsUserContainingUserDetails() {
        final var expectedUserDto = EntityHatchery.getUserDto();
        final var actualUserDto = userMapper.map(EntityHatchery.getUser());

        assertThat(expectedUserDto)
                .usingRecursiveComparison()
                .isEqualTo(actualUserDto);
    }

    /**
     * Checks, if mapper takes Dto with details
     * and returns a correct Mono with Entity.
     */
    @Test
    void shouldReturnValidUser_WhenMapsUserDTOContainingUserDetailsDTO() {
        final var expectedUser = EntityHatchery.getUser();
        final var actualUser = userMapper.map(EntityHatchery.getUserDto());

        assertThat(expectedUser)
                .usingRecursiveComparison()
                .isEqualTo(actualUser);
    }


}
