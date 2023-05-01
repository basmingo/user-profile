package com.iprody.user.profile.persistence.repository;

import com.iprody.user.profile.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Represents a set of operations in the
 * Database with a User.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByEmail(String email);
}
