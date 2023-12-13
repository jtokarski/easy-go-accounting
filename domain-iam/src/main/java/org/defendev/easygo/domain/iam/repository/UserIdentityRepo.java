package org.defendev.easygo.domain.iam.repository;

import org.defendev.easygo.domain.iam.model.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;



public interface UserIdentityRepo extends JpaRepository<UserIdentity, Long> {

    Optional<UserIdentity> findByUsername(String username);

    @Query("SELECT ui FROM UserIdentity ui WHERE ui.oidcProvider = :oidcProvider AND ui.oidcSub = :oidcSub")
    Optional<UserIdentity> findOidcSub(
        @Param("oidcProvider") String oidcProvider,
        @Param("oidcSub") String oidcSub
    );

}
