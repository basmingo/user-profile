package com.iprody.user.profile.persistence.repository;

import com.iprody.user.profile.persistence.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {
}
