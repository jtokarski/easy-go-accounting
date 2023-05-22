package org.defendev.easygo.domain.useridentity.repository;

import org.defendev.easygo.domain.useridentity.model.OwnershipUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface OwnershipUnitRepo extends JpaRepository<OwnershipUnit, Long> {

    @Query(value = "SELECT ou FROM OwnershipUnit ou WHERE ou.anonymousPrivilege IS NOT EMPTY")
    List<OwnershipUnit> findWithAnyAnonymousPrivilege();

}
