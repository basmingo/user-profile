package com.iprody.user.profile.e2e.stepdefs;

import com.iprody.user.profile.e2e.generated.model.ApiError;
import com.iprody.user.profile.e2e.generated.model.UserDetailsDto;
import com.iprody.user.profile.e2e.generated.model.UserDto;
import lombok.experimental.UtilityClass;

import java.util.Map;

/**
 * This utility class provides methods to map expected response data from a {@code Map} to the corresponding objects.
 */
@UtilityClass
public class DataTableMapper {
    /**
     * Maps the given user data from a {@code Map} to a {@code UserDto} object.
     *
     * @param userAsMap the user data to be mapped
     * @return a new {@code UserDto} object with the mapped user data
     */
    public UserDto mapToUserDto(Map<String, String> userAsMap) {
        final UserDto userDto = new UserDto();
        userDto.setId(Long.parseLong(userAsMap.get("id")));
        userDto.setFirstName(userAsMap.get("firstName"));
        userDto.setLastName(userAsMap.get("lastName"));
        userDto.setEmail(userAsMap.get("email"));

        final UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(Long.parseLong(userAsMap.get("userDetails.id")));
        userDetailsDto.setMobilePhone(userAsMap.get("userDetails.mobilePhone"));
        userDetailsDto.setTelegramId(userAsMap.get("userDetails.telegramId"));
        userDetailsDto.setZoneId(userAsMap.get("userDetails.zoneId"));
        userDto.setUserDetails(userDetailsDto);

        return userDto;
    }

    /**
     * Maps the given error data from a {@code Map} to an {@code ApiError} object.
     *
     * @param errorAsMap the error data to be mapped
     * @return a new {@code ApiError} object with the mapped error data
     */
    public ApiError mapToApiError(Map<String, String> errorAsMap) {
        final ApiError apiError = new ApiError();
        apiError.setStatus(Integer.parseInt(errorAsMap.get("status")));
        apiError.setMessage(errorAsMap.get("message"));

        return apiError;
    }
}
