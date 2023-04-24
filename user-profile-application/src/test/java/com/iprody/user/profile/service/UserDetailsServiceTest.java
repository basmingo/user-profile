package com.iprody.user.profile.service;

import com.iprody.user.profile.api.dto.UserDetailsDto;
import com.iprody.user.profile.exception.ResourceNotFoundException;
import com.iprody.user.profile.persistence.entity.UserDetails;
import com.iprody.user.profile.persistence.repository.UserDetailsRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.time.ZoneId;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Integration test, which checks if UserService
 * behaviour works predictably with a combination
 * of PostgreSQL containers.
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
class UserDetailsServiceTest {

    private static final long FIRST_DATABASE_ELEMENT_INDEX = 1;
    private static final long NOT_EXISTING_IN_DB_ELEMENT_INDEX = 4;
    private static final String TELEGRAM_ID = "@TestFirstUser";
    private static final String MOBILE_PHONE = "+ 111 222 333 44";
    private static final String MESSAGE_ERROR = "No UserDetails found with id: " + NOT_EXISTING_IN_DB_ELEMENT_INDEX;

    @Container
    private static final PostgreSQLContainer<?> SQL_CONTAINER =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("user-profile");

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserProfileService userProfileService;

    @BeforeEach
    void initDatabase() {
        final var userDetails = new UserDetails();
        userDetails.setId(FIRST_DATABASE_ELEMENT_INDEX);
        userDetails.setTelegramId(TELEGRAM_ID);
        userDetails.setMobilePhone(MOBILE_PHONE);
        userDetails.setZoneId(ZoneId.systemDefault());

        userDetailsRepository.save(userDetails);
    }

    @AfterAll
    static void tearDownAll() {
        SQL_CONTAINER.close();
    }

    /**
     * Setting up a test container to raise a test database and liquibase.
     *
     * @param registry the registry
     */
    @DynamicPropertySource
    public static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url=", SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username=", SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password=", SQL_CONTAINER::getPassword);

        registry.add("spring.liquibase.url", SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.liquibase.user", SQL_CONTAINER::getUsername);
        registry.add("spring.liquibase.password", SQL_CONTAINER::getPassword);

        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
    }

    @Test
    @Order(1)
    void shouldUpdateGivenUserDetailsIntoDatabase_andRetrieveNewlyUpdatedRecordWithIdAssigned() {
        final var testCreatedUserDetails = existingUserDetailsDto();

        final var returnedUpdateUserDetails = userProfileService
                .updateUserDetails(NOT_EXISTING_IN_DB_ELEMENT_INDEX,
                        testCreatedUserDetails.getId(), testCreatedUserDetails);

        StepVerifier
                .create(returnedUpdateUserDetails)
                .expectNextMatches(existingUserDetailsDto()::equals)
                .verifyComplete();
    }

    @Test
    @Order(2)
    void shouldThrowResourceNotFoundException_uponAttemptToUpdateUserDetails_becauseRecordWithGivenIdDoesNotExists() {
        final var testCreatedUserDetails = existingUserDetailsDto();

        final var returnedUpdateUserDetails = userProfileService
                .updateUserDetails(NOT_EXISTING_IN_DB_ELEMENT_INDEX,
                        NOT_EXISTING_IN_DB_ELEMENT_INDEX, testCreatedUserDetails);

        StepVerifier
                .create(returnedUpdateUserDetails)
                .verifyErrorSatisfies(th ->
                        assertThat(th)
                                .isExactlyInstanceOf(ResourceNotFoundException.class)
                                .hasMessage(MESSAGE_ERROR));
    }

    @Test
    @Order(3)
    void shouldFindGivenUserDetailsIntoDatabase_andRetrieveExistUserDetailsWithIdAssigned() {
        final var testCreatedUserDetails = existingUserDetailsDto();

        final var returnedUserDetails = userProfileService.getUserDetails(NOT_EXISTING_IN_DB_ELEMENT_INDEX,
                testCreatedUserDetails.getId());

        StepVerifier
                .create(returnedUserDetails)
                .expectNext(existingUserDetailsDto())
                .verifyComplete();
    }

    @Test
    @Order(4)
    void shouldThrowResourceNotFoundException_uponAttemptToGetUserDetails_becauseRecordWithGivenIdDoesNotExists() {
        final var returnedUserDetails = userProfileService.getUserDetails(NOT_EXISTING_IN_DB_ELEMENT_INDEX,
                NOT_EXISTING_IN_DB_ELEMENT_INDEX);

        StepVerifier
                .create(returnedUserDetails)
                .verifyErrorSatisfies(th ->
                        assertThat(th)
                                .isExactlyInstanceOf(ResourceNotFoundException.class)
                                .hasMessage(MESSAGE_ERROR));
    }

    private UserDetailsDto existingUserDetailsDto() {
        final var userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(FIRST_DATABASE_ELEMENT_INDEX);
        userDetailsDto.setTelegramId(TELEGRAM_ID);
        userDetailsDto.setMobilePhone(MOBILE_PHONE);
        userDetailsDto.setZoneId(ZoneId.systemDefault());

        return userDetailsDto;
    }
}
