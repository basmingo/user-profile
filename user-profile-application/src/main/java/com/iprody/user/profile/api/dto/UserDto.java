package com.iprody.user.profile.api.dto;

import com.iprody.user.profile.api.dto.validation.NonExistingEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO for the User entity.
 */

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Information about user")
public class UserDto {

    /**
     * ID of the user dto.
     */
    private long id;
    /**
     * First name of the user.
     */
    @NotBlank(message = "First Name is mandatory")
    @Size(max = 30, message = "First Name must be less than 30 characters")
    private String firstName;
    /**
     * Last name of the user.
     */
    @NotBlank(message = "Last Name is mandatory")
    @Size(max = 30, message = "First Name must be less than 30 characters")
    private String lastName;
    /**
     * Email of the user.
     */
    @NonExistingEmail
    private String email;
    /**
     * Nested object with details of the user.
     */
    @Valid
    @NotNull
    private UserDetailsDto userDetails;
}
