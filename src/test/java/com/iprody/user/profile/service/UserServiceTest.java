package com.iprody.user.profile.service;

import com.iprody.user.profile.api.dto.UserDetailsDto;
import com.iprody.user.profile.api.dto.UserDto;
import com.iprody.user.profile.exception.ResourceNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test, which checks if UserService
 * behaviour works predictably with a combination
 * of PostgreSQL containers. <p>
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
class UserServiceTest {
    private static final long FIRST_DATABASE_ELEMENT_INDEX = 1;
    private static final long UNEXISTING_IN_DB_ELEMENT_INDEX = 5;
    private static final String FIRST_NAME = "TestFirstName";
    private static final String LAST_NAME = "TestLastName";
    private static final String LAST_NAME_UPDATED = "TestLastNameUpdated";
    private static final String TELEGRAM_ID = "TestTelegramId";
    private static final String MOBILE_PHONE = "TestMobilePhone";
    private static final String EMAIL_ADDRESS = "TestEmail@gmail.com";

    @Container
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("user-profile");

    @Autowired
    private UserProfileService userProfileService;

    @AfterAll
    public static void tearDown() {
        POSTGRE_SQL_CONTAINER.close();
    }

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.liquibase.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.liquibase.user", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("spring.liquibase.password", POSTGRE_SQL_CONTAINER::getPassword);

        registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);

        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
    }

    /**
     * Checks if create a User with Details return
     * Object with the Id.
     */
    @Test
    @Order(1)
    void shouldSaveGivenUserIntoDatabase_andRetrieveNewlyCreatedRecordWithIdAssigned() {
        final var returnedUser = userProfileService.createUser(getUserDto());
        StepVerifier
                .create(returnedUser)
                .expectNextMatches(el -> el.getId() == FIRST_DATABASE_ELEMENT_INDEX)
                .verifyComplete();
    }

    /**
     * Checks if update a User with Details return
     * Object with updated field.
     */
    @Test
    @Order(2)
    void shouldReturnObjectWithId_WhenUserUpdateWithDetails() {
        final var userDto = getUserDtoWithIdAndLastNameUpdated();
        final var updatedUser = userProfileService.updateUser(userDto.getId(), userDto);
        StepVerifier
                .create(updatedUser)
                .expectNextMatches(userDto::equals)
                .verifyComplete();
    }

    /**
     * Checks, if find User with details returns
     * an object with valid id.
     */
    @Test
    @Order(3)
    void shouldFindUser_whenRecordMatchesGivenId() {
        StepVerifier
                .create(userProfileService.getUserWithDetails(FIRST_DATABASE_ELEMENT_INDEX))
                .expectNextMatches(el -> el.getId() == FIRST_DATABASE_ELEMENT_INDEX)
                .verifyComplete();
    }

    /**
     * Checks, if find not existed User with details returns
     * an Exception.
     */
    @Test
    @Order(4)
    void shouldReturnException_whenRecordNotMatchesGivenId() {
        StepVerifier
                .create(userProfileService.getUserWithDetails(UNEXISTING_IN_DB_ELEMENT_INDEX))
                .verifyErrorSatisfies(th -> assertThat(th)
                        .isExactlyInstanceOf(ResourceNotFoundException.class)
                        .hasMessage("No user found with id: " + UNEXISTING_IN_DB_ELEMENT_INDEX)
                );
    }

    private UserDetailsDto getUserDetailsDto() {
        final var userDetailsDto = new UserDetailsDto();
        userDetailsDto.setMobilePhone(MOBILE_PHONE);
        userDetailsDto.setTelegramId(TELEGRAM_ID);
        userDetailsDto.setZoneId(ZoneId.systemDefault());
        return userDetailsDto;
    }

    private UserDetailsDto getUserDetailsDtoWithId() {
        final var userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(FIRST_DATABASE_ELEMENT_INDEX);
        userDetailsDto.setMobilePhone(MOBILE_PHONE);
        userDetailsDto.setTelegramId(TELEGRAM_ID);
        userDetailsDto.setZoneId(ZoneId.systemDefault());
        return userDetailsDto;
    }

    private UserDto getUserDto() {
        final var userDto = new UserDto();
        userDto.setFirstName(FIRST_NAME);
        userDto.setLastName(LAST_NAME);
        userDto.setEmail(EMAIL_ADDRESS);
        userDto.setUserDetails(getUserDetailsDto());
        return userDto;
    }

    private UserDto getUserDtoWithIdAndLastNameUpdated() {
        final var userDto = new UserDto();
        userDto.setId(FIRST_DATABASE_ELEMENT_INDEX);
        userDto.setFirstName(FIRST_NAME);
        userDto.setLastName(LAST_NAME_UPDATED);
        userDto.setEmail(EMAIL_ADDRESS);
        userDto.setUserDetails(getUserDetailsDtoWithId());
        return userDto;
    }
}
