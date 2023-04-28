package com.iprody.user.profile.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;

/**
 * A DTO for the UserDetails entity.
 */

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Information about user details")
public class UserDetailsDto {

    /**
     * ID of the user details dto.
     */
    private long id;
    /**
     * Telegram ID of the user details dto.
     * Possible formats of telegram ID.
     * <p>
     * "@telegram_100"
     * https://t.me/telegram_100
     * https://telegram.me/telegram_100
     * http://t.me/telegram_100
     * https://t.me/telegram_100
     */
    @NotNull(message = "Telegram id cannot be null")
    @Pattern(regexp = "(?:@|(?:(?:(?:https?://)?t(?:elegram)?)\\.me\\/))(\\w{4,})$")
    private String telegramId;
    /**
     * Mobile phone of the user details dto.
     * Possible formats of mobile phone.
     * <p>
     * "2055550125","202 555 0125", "(202) 555-0125", "+111 (202) 555-0125",
     * "636 856 789", "+111 636 856 789", "636 85 67 89", "+111 636 85 67 89"
     */
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$")
    private String mobilePhone;
    /**
     * A timezone of the user.
     */
    @NotNull(message = "Timezone cannot be null")
    @Schema(type = "string", description="A time-zone ID, such as Europe/Paris.", example = "Europe/Paris")
    private ZoneId zoneId;
}
