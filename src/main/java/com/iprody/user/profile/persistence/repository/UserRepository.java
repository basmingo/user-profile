package com.iprody.user.profile.persistence.repository;

import com.iprody.user.profile.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
