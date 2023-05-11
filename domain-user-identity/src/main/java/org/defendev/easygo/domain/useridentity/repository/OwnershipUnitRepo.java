package org.defendev.easygo.domain.useridentity.repository;

import org.defendev.easygo.domain.useridentity.model.OwnershipUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface OwnershipUnitRepo extends JpaRepository<OwnershipUnit, Long> {

    List<OwnershipUnit> findOwnershipUnitByPublicReadTrue();

}
