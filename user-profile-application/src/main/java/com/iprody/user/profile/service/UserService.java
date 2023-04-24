package com.iprody.user.profile.service;

import com.iprody.user.profile.exception.ResourceNotFoundException;
import com.iprody.user.profile.persistence.entity.User;
import com.iprody.user.profile.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Represents a Service, containing a set of
 * operations with a User.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * Message for the ResourceNotFoundException.
     */
    private static final String EXCEPTION_MESSAGE = "No user found with id: ";

    /**
     * Autowired Repository, for the Database operations.
     */
    private final UserRepository userRepository;

    /**
     * Save a User, if not exists in the Database.
     *
     * @param user without ID, or with ID, that is not existing in the Database.
     * @return Mono with User, if required is not exists in the Database. <p>
     * Empty Mono, if required user exists.
     */
    public Mono<User> createUser(User user) {
        return Mono.just(userRepository.save(user));
    }

    /**
     * Update User, if exists in the Database.
     *
     * @param user that should be attached to the user;
     * @return Mono with User, if required exists in the Database. <p>
     * Empty Mono, if required user is not existed.
     */
    public Mono<User> updateUser(User user) {
        return Mono.justOrEmpty(userRepository.save(user))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(EXCEPTION_MESSAGE + user.getId())));
    }

    /**
     * Finding a User, if exists in the Database.
     *
     * @param id unique Identifier of the User, which should be
     *           returned.
     * @return Mono, containing a User, in case if an ID is attached
     * to any User in the Database. <p>
     * Empty Mono, if there is no such User, with required ID.
     */
    public Mono<User> getUserWithDetails(long id) {
        return Mono.justOrEmpty(userRepository.findById(id))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(EXCEPTION_MESSAGE + id)));
    }

    /**
     * Checks, if a User exists in the Database.
     * @param id unique Identifier of the User, which should be
     *           returned.
     * @return Mono, containing: <p>
     * true, if User with required id exists. <p>
     * false, if User with required id, doesn't exist.
     */
    public Mono<Boolean> existsById(long id) {
        return Mono.just(userRepository.existsById(id));
    }
}
