package org.defendev.easygo.domain.useridentity.repository;

import org.defendev.easygo.domain.useridentity.model.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface UserIdentityRepo extends JpaRepository<UserIdentity, Long> {

    Optional<UserIdentity> findByUsername(String username);

}
