package com.iprody.user.profile.persistence.repository;

import com.iprody.user.profile.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents a set of operations in the
 * Database with a User.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
