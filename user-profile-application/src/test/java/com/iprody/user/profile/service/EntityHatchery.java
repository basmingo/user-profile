package com.iprody.user.profile.service;

import com.iprody.user.profile.api.dto.UserDetailsDto;
import com.iprody.user.profile.api.dto.UserDto;
import com.iprody.user.profile.persistence.entity.User;
import com.iprody.user.profile.persistence.entity.UserDetails;
import lombok.experimental.UtilityClass;

import java.time.ZoneId;

/**
 * The type Entity creator for tests.
 */
@UtilityClass
public final class EntityHatchery {

    /**
     * The Telegram id.
     */
    private String TELEGRAM_ID = "@TestUser";
    /**
     * The Mobile phone.
     */
    private String MOBILE_PHONE = "+ 111 222 333 44";
    /**
     * The First name.
     */
    private String FIRST_NAME = "TestFirstName";
    /**
     * The Last name.
     */
    private String LAST_NAME = "TestLastName";
    /**
     * The last name updated.
     */
    private String LAST_NAME_UPDATED = "TestLastNameUpdated";
    /**
     * The Email address.
     */
    private String EMAIL_ADDRESS = "TestEmail@gmail.com";
    /**
     * The First element id.
     */
    private long FIRST_ELEMENT_ID = 1;

    /**
     * Not existing element id.
     */
    private long NOT_EXISTING_IN_DB_ELEMENT_INDEX = 5;

    /**
     * Creating UserDetails test data.
     *
     * @return userDetails user details
     */

    public UserDetails getUserDetails() {
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
     * @return userDetails user details dto
     */
    public UserDetailsDto getUserDetailsDto() {
        final var userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(FIRST_ELEMENT_ID);
        userDetailsDto.setTelegramId(TELEGRAM_ID);
        userDetailsDto.setMobilePhone(MOBILE_PHONE);
        userDetailsDto.setZoneId(ZoneId.systemDefault());

        return userDetailsDto;
    }

    /**
     * Creating user for tests.
     *
     * @return the user
     */
    public User getUser() {
        final var user = new User();
        user.setId(FIRST_ELEMENT_ID);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setEmail(EMAIL_ADDRESS);
        user.setUserDetails(EntityHatchery.getUserDetails());

        return user;
    }

    /**
     * Creating userDto for tests.
     *
     * @return the user dto
     */
    public UserDto getUserDto() {
        final var userDto = new UserDto();
        userDto.setId(FIRST_ELEMENT_ID);
        userDto.setFirstName(FIRST_NAME);
        userDto.setLastName(LAST_NAME);
        userDto.setEmail(EMAIL_ADDRESS);
        userDto.setUserDetails(EntityHatchery.getUserDetailsDto());

        return userDto;
    }

    /**
     * Gets user dto with id and last name updated.
     *
     * @return the user dto with id and last name updated
     */
    public UserDto getUserDtoWithIdAndLastNameUpdated() {
        final var userDto = new UserDto();
        userDto.setId(FIRST_ELEMENT_ID);
        userDto.setFirstName(FIRST_NAME);
        userDto.setLastName(LAST_NAME_UPDATED);
        userDto.setEmail(EMAIL_ADDRESS);
        userDto.setUserDetails(EntityHatchery.getUserDetailsDto());

        return userDto;
    }

    /**
     * Gets user dto with not existing id and last name updated.
     *
     * @return the user dto with not existing id and last name updated
     */
    public UserDto getUserDtoWithNotExistingIdAndLastNameUpdated() {
        final var userDto = new UserDto();
        userDto.setId(NOT_EXISTING_IN_DB_ELEMENT_INDEX);
        userDto.setFirstName(FIRST_NAME);
        userDto.setLastName(LAST_NAME_UPDATED);
        userDto.setEmail(EMAIL_ADDRESS);
        userDto.setUserDetails(EntityHatchery.getUserDetailsDto());

        return userDto;
    }
}
