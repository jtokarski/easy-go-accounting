package org.defendev.easygo.domain.iam.service.dto;

import org.defendev.easygo.domain.iam.api.Privilege;

import java.util.Map;
import java.util.Set;



public interface IEasygoUserDetails {

    String getUsername();

    Map<Privilege, Set<Long>> getPrivilegeToOwnershipUnit();

}
