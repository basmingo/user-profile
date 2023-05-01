package com.iprody.user.profile.controller;

import com.iprody.user.profile.api.controller.UserProfileController;
import com.iprody.user.profile.api.dto.UserDetailsDto;
import com.iprody.user.profile.api.dto.UserDto;
import com.iprody.user.profile.api.dto.validation.UniqueEmailValidator;
import com.iprody.user.profile.exception.ResourceNotFoundException;
import com.iprody.user.profile.exception.ResourceProcessingException;
import com.iprody.user.profile.persistence.repository.UserRepository;
import com.iprody.user.profile.service.UserProfileService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.ZoneId;
import java.util.stream.Stream;

@WebFluxTest(controllers = UserProfileController.class)
@Import(value = UniqueEmailValidator.class)
class UserProfileControllerTest {
    private final String baseUrl = "/users";
    private final String urlId = "/1";
    private final String urlIdDetails = "/1/details/1";
    private final String jsonId = "$.id";
    private final String jsonFirstName = "$.firstName";
    private final String jsonLastName = "$.lastName";
    private final String jsonEmail = "$.email";
    private final String jsonTelegramId = "$.telegramId";
    private final String jsonMobilePhone = "$.mobilePhone";
    private final String jsonStatus = "$.status";
    private final String jsonMessage = "$.message";
    private final String jsonDetails = "$.details";
    private final String userNotFoundMessage = "User not found";
    private final String userDetailsNotFoundMessage = "User details not found";
    private final String emailAlreadyExists = "User with this email address already exists";
    private final String status500message = "Something went wrong";
    private final String methodSourcePath = "com.iprody.user.profile.controller."
            + "UserProfileControllerTest#";

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private UserProfileService userProfileService;

    @Autowired
    private UniqueEmailValidator uniqueEmailValidator;

    @MockBean
    private UserRepository userRepository;

    @Nested
    class CreateUserTest {
        @Test
        void shouldCreateUser_thenReturnSavedUser_soResponseIs201() {
            final UserDto userDto = getCorrectUserDto();
            Mockito.when(userProfileService.createUser(userDto)).thenReturn(Mono.just(userDto));
            Mockito.when(userRepository.existsByEmail(Mockito.any())).thenReturn(false);

            final WebTestClient.ResponseSpec response = webTestClient.post()
                    .uri(baseUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(getCorrectUserDto())
                    .exchange();

            response.expectStatus()
                    .isCreated()
                    .expectHeader().doesNotExist("Location")
                    .expectBody()
                    .consumeWith(System.out::println)
                    .jsonPath(jsonId).isEqualTo(1)
                    .jsonPath(jsonFirstName).isEqualTo(userDto.getFirstName())
                    .jsonPath(jsonLastName).isEqualTo(userDto.getLastName())
                    .jsonPath(jsonEmail).isEqualTo(userDto.getEmail());
        }

        @ParameterizedTest
        @MethodSource(methodSourcePath + "provideInvalidUserDto")
        void shouldNotCreateUser_becauseRequestValidationFailed_soResponseIs400(UserDto invalidUserDto) {
            Mockito.when(userRepository.existsByEmail(Mockito.any())).thenReturn(false);
            final WebTestClient.ResponseSpec response = webTestClient.post()
                    .uri(baseUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(invalidUserDto)
                    .exchange();

            response.expectStatus()
                    .isBadRequest()
                    .expectBody()
                    .consumeWith(System.out::println)
                    .jsonPath(jsonStatus).exists()
                    .jsonPath(jsonMessage).exists()
                    .jsonPath(jsonDetails).exists();
        }

        @Test
        void shouldNotCreateUser_becauseEmailAlreadyExists_soResponseIs400() {
            Mockito.when(userRepository.existsByEmail(Mockito.any())).thenReturn(true);

            final WebTestClient.ResponseSpec response = webTestClient.post()
                    .uri(baseUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(getCorrectUserDto())
                    .exchange();

            response.expectStatus()
                    .is4xxClientError()
                    .expectBody()
                    .consumeWith(System.out::println)
                    .jsonPath(jsonStatus).exists()
                    .jsonPath(jsonMessage).exists()
                    .jsonPath(jsonDetails).exists();
        }

        @Test
        void shouldNotCreateUser_becauseInternalServerError_soResponseIs500() {
            final UserDto userDto = getCorrectUserDto();
            Mockito.when(userProfileService.createUser(userDto))
                    .thenThrow(new ResourceProcessingException(status500message));

            final WebTestClient.ResponseSpec response = webTestClient.post()
                    .uri(baseUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(getCorrectUserDto())
                    .exchange();

            response.expectStatus()
                    .is5xxServerError()
                    .expectBody()
                    .consumeWith(System.out::println)
                    .jsonPath(jsonStatus).exists()
                    .jsonPath(jsonMessage).exists()
                    .jsonPath(jsonDetails).exists();
        }
    }

    @Nested
    class UpdateUser {
        @Test
        void shouldUpdateUser_thenReturnUpdatedUser_soResponseIs200() {
            final UserDto userDto = getCorrectUserDto();
            Mockito.when(userProfileService.updateUser(1, userDto)).thenReturn(Mono.just(userDto));
            final WebTestClient.ResponseSpec response = webTestClient.put()
                    .uri(baseUrl + urlId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(userDto)
                    .exchange();

            response.expectStatus()
                    .isOk()
                    .expectBody()
                    .jsonPath(jsonId).isEqualTo(1)
                    .jsonPath(jsonFirstName).isEqualTo(userDto.getFirstName())
                    .jsonPath(jsonLastName).isEqualTo(userDto.getLastName())
                    .jsonPath(jsonEmail).isEqualTo(userDto.getEmail());
        }

        @ParameterizedTest
        @MethodSource(methodSourcePath + "provideInvalidUserDto")
        void shouldNotUpdateUser_becauseRequestValidationFailed_soResponseIs400(UserDto invalidUserDto) {
            final WebTestClient.ResponseSpec response = webTestClient.put()
                    .uri(baseUrl + urlId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(invalidUserDto)
                    .exchange();

            response.expectStatus()
                    .isBadRequest()
                    .expectBody()
                    .consumeWith(System.out::println)
                    .jsonPath(jsonStatus).exists()
                    .jsonPath(jsonMessage).exists()
                    .jsonPath(jsonDetails).exists();
        }

        @Test
        void shouldNotUpdateUser_becauseEmailAlreadyExists_soResponseIs400() {
            final UserDto userDto = getCorrectUserDto();
            Mockito.when(userProfileService.updateUser(1, userDto))
                    .thenThrow(new ResourceNotFoundException(emailAlreadyExists));
            Mockito.when(userRepository.existsByEmail(Mockito.any())).thenReturn(true);
            final WebTestClient.ResponseSpec response = webTestClient.put()
                    .uri(baseUrl + urlId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(userDto)
                    .exchange();

            response.expectStatus()
                    .is4xxClientError()
                    .expectBody()
                    .jsonPath(jsonStatus).exists()
                    .jsonPath(jsonMessage).exists()
                    .jsonPath(jsonDetails).exists();
        }

        @Test
        void shouldNotUpdateUser_becauseUserIsNotFound_soResponseIs404() {
            final UserDto userDto = getCorrectUserDto();
            Mockito.when(userProfileService.updateUser(1, userDto))
                    .thenThrow(new ResourceNotFoundException(userNotFoundMessage));
            final WebTestClient.ResponseSpec response = webTestClient.put()
                    .uri(baseUrl + urlId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(userDto)
                    .exchange();

            response.expectStatus()
                    .isNotFound()
                    .expectBody()
                    .consumeWith(System.out::println)
                    .jsonPath(jsonStatus).exists()
                    .jsonPath(jsonMessage).exists()
                    .jsonPath(jsonDetails).exists();
        }



        @Test
        void shouldNotUpdateUser_becauseInternalServerError_soResponseIs500() {
            final UserDto userDto = getCorrectUserDto();
            Mockito.when(userProfileService.updateUser(1, userDto))
                    .thenThrow(new ResourceProcessingException(status500message));
            Mockito.when(userRepository.existsByEmail(Mockito.any())).thenReturn(false);
            final WebTestClient.ResponseSpec response = webTestClient.put()
                    .uri(baseUrl + urlId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(userDto)
                    .exchange();

            response.expectStatus()
                    .is5xxServerError()
                    .expectBody()
                    .consumeWith(System.out::println)
                    .jsonPath(jsonStatus).exists()
                    .jsonPath(jsonMessage).exists()
                    .jsonPath(jsonDetails).exists();
        }
    }

    @Nested
    class UpdateUserDetails {
        @Test
        void shouldUpdateUserDetails_thenReturnUserDetails_soResponseIs200() {
            final UserDetailsDto userDetailsDto = getCorrectUserDto().getUserDetails();
            Mockito.when(userProfileService.updateUserDetails(0, 0, userDetailsDto))
                    .thenReturn(Mono.just(userDetailsDto));
            final WebTestClient.ResponseSpec response = webTestClient.put()
                    .uri(baseUrl + "/0/details/0")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(userDetailsDto)
                    .exchange();

            response.expectStatus()
                    .isOk()
                    .expectBody()
                    .jsonPath(jsonId).isEqualTo(1)
                    .jsonPath(jsonTelegramId).isEqualTo(userDetailsDto.getTelegramId())
                    .jsonPath(jsonMobilePhone).isEqualTo(userDetailsDto.getMobilePhone());
        }

        @ParameterizedTest
        @MethodSource(methodSourcePath + "provideInvalidUserDetailsDto")
        void shouldNotUpdateUserDetails_becauseRequestValidationFailed_soResponseIs400(
                UserDetailsDto invalidUserDetailsDto) {
            final WebTestClient.ResponseSpec response = webTestClient.put()
                    .uri(baseUrl + urlIdDetails)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(invalidUserDetailsDto)
                    .exchange();

            response.expectStatus()
                    .isBadRequest()
                    .expectBody()
                    .consumeWith(System.out::println)
                    .jsonPath(jsonStatus).exists()
                    .jsonPath(jsonMessage).exists()
                    .jsonPath(jsonDetails).exists();
        }

        @Test
        void shouldNotUpdateUserDetails_becauseUserDetailsIsNotFound_soResponseIs404() {
            final UserDetailsDto userDetailsDto = getCorrectUserDto().getUserDetails();
            Mockito.when(userProfileService.updateUserDetails(1, 1, userDetailsDto))
                    .thenThrow(new ResourceNotFoundException(userDetailsNotFoundMessage));
            final WebTestClient.ResponseSpec response = webTestClient.put()
                    .uri(baseUrl + urlIdDetails)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(userDetailsDto)
                    .exchange();

            response.expectStatus()
                    .isNotFound()
                    .expectBody()
                    .consumeWith(System.out::println)
                    .jsonPath(jsonStatus).exists()
                    .jsonPath(jsonMessage).exists()
                    .jsonPath(jsonDetails).exists();
        }

        @Test
        void shouldNotUpdateUserDetails_becauseInternalServerError_soResponseIs500() {
            final UserDetailsDto userDetailsDto = getCorrectUserDto().getUserDetails();
            Mockito.when(userProfileService.updateUserDetails(1, 1, userDetailsDto))
                    .thenThrow(new ResourceProcessingException(status500message));
            final WebTestClient.ResponseSpec response = webTestClient.put()
                    .uri(baseUrl + urlIdDetails)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(userDetailsDto)
                    .exchange();

            response.expectStatus()
                    .is5xxServerError()
                    .expectBody()
                    .consumeWith(System.out::println)
                    .jsonPath(jsonStatus).exists()
                    .jsonPath(jsonMessage).exists()
                    .jsonPath(jsonDetails).exists();
        }
    }

    @Nested
    class GetUserWithDetails {
        @Test
        void shouldReturnUserWithDetails_forGivenUserId_soResponseIs200() {
            final UserDto userDto = getCorrectUserDto();
            Mockito.when(userProfileService.getUserWithDetails(1))
                    .thenReturn(Mono.just(userDto));
            final WebTestClient.ResponseSpec response = webTestClient.get()
                    .uri(baseUrl + urlId)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange();

            response.expectStatus()
                    .isOk()
                    .expectBody()
                    .jsonPath(jsonId).isEqualTo(1)
                    .jsonPath(jsonFirstName).isEqualTo(userDto.getFirstName())
                    .jsonPath(jsonLastName).isEqualTo(userDto.getLastName())
                    .jsonPath("$.userDetails.telegramId").isEqualTo(userDto.getUserDetails().getTelegramId())
                    .jsonPath(jsonEmail).isEqualTo(userDto.getEmail());
        }

        @Test
        void shouldNotReturnUserWithDetails_forGivenUserId_becauseUserNotFound_soResponseIs404() {
            Mockito.when(userProfileService.getUserWithDetails(1))
                    .thenThrow(new ResourceNotFoundException(userNotFoundMessage));
            final WebTestClient.ResponseSpec response = webTestClient.get()
                    .uri(baseUrl + urlId)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange();

            response.expectStatus()
                    .isNotFound()
                    .expectBody()
                    .consumeWith(System.out::println)
                    .jsonPath(jsonStatus).exists()
                    .jsonPath(jsonMessage).exists()
                    .jsonPath(jsonDetails).exists();
        }

        @Test
        void shouldNotReturnUserWithDetails_forGivenUserId_becauseInternalServerError_soResponseIs500() {
            Mockito.when(userProfileService.getUserWithDetails(1))
                    .thenThrow(new ResourceProcessingException(status500message));
            final WebTestClient.ResponseSpec response = webTestClient.get()
                    .uri(baseUrl + urlId)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange();

            response.expectStatus()
                    .is5xxServerError()
                    .expectBody()
                    .consumeWith(System.out::println)
                    .jsonPath(jsonStatus).exists()
                    .jsonPath(jsonMessage).exists()
                    .jsonPath(jsonDetails).exists();
        }
    }

    @Nested
    class GetUserDetails {
        @Test
        void shouldReturnUserDetails_forGivenUserDetailsId_soResponseIs200() {
            final UserDetailsDto userDetailsDto = getCorrectUserDto().getUserDetails();
            Mockito.when(userProfileService.getUserDetails(1, 1))
                    .thenReturn(Mono.just(userDetailsDto));
            final WebTestClient.ResponseSpec response = webTestClient.get()
                    .uri(baseUrl + urlIdDetails)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange();

            response.expectStatus()
                    .isOk()
                    .expectBody()
                    .jsonPath(jsonId).isEqualTo(1)
                    .jsonPath(jsonTelegramId).isEqualTo(userDetailsDto.getTelegramId())
                    .jsonPath(jsonMobilePhone).isEqualTo(userDetailsDto.getMobilePhone());
        }

        @Test
        void shouldNotReturnUserDetails_forGivenUserDetailsId_becauseNotFound_soResponseIs404() {
            Mockito.when(userProfileService.getUserDetails(1, 1))
                    .thenThrow(new ResourceNotFoundException(userDetailsNotFoundMessage));
            final WebTestClient.ResponseSpec response = webTestClient.get()
                    .uri(baseUrl + urlIdDetails)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange();

            response.expectStatus()
                    .isNotFound()
                    .expectBody()
                    .consumeWith(System.out::println)
                    .jsonPath(jsonStatus).exists()
                    .jsonPath(jsonMessage).exists()
                    .jsonPath(jsonDetails).exists();
        }

        @Test
        void shouldNotReturnUserDetails_forGivenUserDetailsId_becauseInternalServerError_soResponseIs500() {
            Mockito.when(userProfileService.getUserDetails(1, 1))
                    .thenThrow(new ResourceProcessingException(status500message));
            final WebTestClient.ResponseSpec response = webTestClient.get()
                    .uri(baseUrl + urlIdDetails)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange();

            response.expectStatus()
                    .is5xxServerError()
                    .expectBody()
                    .consumeWith(System.out::println)
                    .jsonPath(jsonStatus).exists()
                    .jsonPath(jsonMessage).exists()
                    .jsonPath(jsonDetails).exists();
        }
    }

    private UserDto getCorrectUserDto() {
        final UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(1);
        userDetailsDto.setTelegramId("@telegram");
        userDetailsDto.setMobilePhone("+111 123 456 789");
        userDetailsDto.setZoneId(ZoneId.of("Europe/Sofia"));
        final UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setFirstName("firstname");
        userDto.setLastName("lastname");
        userDto.setEmail("test@test.com");
        userDto.setUserDetails(userDetailsDto);
        return userDto;
    }

    static Stream<Arguments> provideInvalidUserDetailsDto() {
        final ZoneId zoneId = ZoneId.of("America/New_York");
        System.out.println(zoneId);
        return Stream.of(
                Arguments.of(new UserDetailsDto(0, null, "+111 123 456 781", zoneId)),
                Arguments.of(new UserDetailsDto(0, "", "+111 123 456 782", zoneId)),
                Arguments.of(new UserDetailsDto(0, "@telegramid", "+test", zoneId)),
                Arguments.of(new UserDetailsDto(0, "@telegramid0", "+111 123 456 791", null))
        );
    }


    static Stream<Arguments> provideInvalidUserDto() {
        final ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        return Stream.of(
                Arguments.of(new UserDto(0, "", "lastname0", "test1@test.com",
                        new UserDetailsDto(0, "@telegramid1", "+111 123 456 783", zoneId))),
                Arguments.of(new UserDto(0, "firstname1", "", "test2@test.com",
                        new UserDetailsDto(0, "@telegramid2", "+111 123 456 784", zoneId))),
                Arguments.of(new UserDto(0, "firstname2", "lastname1", "",
                        new UserDetailsDto(0, "@telegramid3", "+111 123 456 785", zoneId))),
                Arguments.of(new UserDto(0, "firstname3", "lastname2", "test3@test.com",
                        null)),
                Arguments.of(new UserDto(0, "firstname4", "lastname3", "test4@test.com",
                        new UserDetailsDto(0, null, "+111 123 456 786", zoneId))),
                Arguments.of(new UserDto(0, "firstname5", "lastname4", "",
                        new UserDetailsDto(0, "@telegramid4", "test", zoneId))),
                Arguments.of(new UserDto(0, "firstname6", "lastname5", "",
                        new UserDetailsDto(0, "@telegramid6", "+111 123 456 792", null)))
        );
    }
}
