package com.iprody.user.profile.service;

import com.iprody.user.profile.exception.ResourceNotFoundException;
import com.iprody.user.profile.persistence.entity.UserDetails;
import com.iprody.user.profile.persistence.repository.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Represents a class, with a set of operations on UserDetails.
 * <p>
 * Implements a Facade design pattern, retraces and map
 * objects on Services, which represent operations
 * on User and UserDetails.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsService {

    /**
     * It's a constructor injection.
     */
    private final UserDetailsRepository userDetailsRepository;

    /**
     * Update the user details for the given user id and user details id.
     *
     * @param userDetails the user details.
     * @return the Mono<UserDetails>.
     */
    public Mono<UserDetails> updateUserDetails(UserDetails userDetails) {
        return Mono.just(userDetailsRepository.save(userDetails));
    }

    /**
     * Find the user details for the given user id user details id.
     *
     * @param id the user details.
     * @return the Mono<UserDetails> or exception.
     */
    public Mono<UserDetails> getUserDetails(long id) {
        return Mono.justOrEmpty(userDetailsRepository.findById(id))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No UserDetails found with id: " + id)));
    }

    /**
     * Checking if an id exists in the database.
     *
     * @param id of the exist user details.
     * @return the Mono<Boolean>.
     */
    public Mono<Boolean> existsById(long id) {
        return Mono.justOrEmpty(userDetailsRepository.existsById(id));
    }
}
