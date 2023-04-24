package com.iprody.user.profile.service;

import com.iprody.user.profile.api.dto.UserDetailsDto;
import com.iprody.user.profile.persistence.entity.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Represents a service, with a set of operations
 * for mapping DTO to the Entity, and Entity to DTO.
 */
@Service
public class UserDetailsMapper {

    /**
     * @param userDetailsDto DTO object, which represents
     *                       a UserDetails.
     * @return a UserDetails object, which could be persisted
     * by the Database.
     */
    public UserDetails map(UserDetailsDto userDetailsDto) {
        final var userDetails = new UserDetails();

        userDetails.setId(userDetailsDto.getId());
        userDetails.setTelegramId(userDetailsDto.getTelegramId());
        userDetails.setMobilePhone(userDetailsDto.getMobilePhone());
        userDetails.setZoneId(userDetailsDto.getZoneId());

        return userDetails;
    }

    /**
     * @param userDetails an Entity object, that should be transformed
     *                    to the DTO.
     * @return DTO, which represents a UserDetails.
     */
    public UserDetailsDto map(UserDetails userDetails) {
        final var userDetailsDto = new UserDetailsDto();

        userDetailsDto.setId(userDetails.getId());
        userDetailsDto.setTelegramId(userDetails.getTelegramId());
        userDetailsDto.setMobilePhone(userDetails.getMobilePhone());
        userDetailsDto.setZoneId(userDetails.getZoneId());

        return userDetailsDto;
    }
}
