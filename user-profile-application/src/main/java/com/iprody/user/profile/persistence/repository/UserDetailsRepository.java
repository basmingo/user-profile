package com.iprody.user.profile.persistence.repository;

import com.iprody.user.profile.persistence.entity.UserDetails;
import org.springframework.data.repository.CrudRepository;

public interface UserDetailsRepository extends CrudRepository<UserDetails, Long> {
}
