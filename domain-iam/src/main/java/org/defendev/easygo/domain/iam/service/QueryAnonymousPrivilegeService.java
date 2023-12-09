package org.defendev.easygo.domain.iam.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.domain.iam.Privilege;
import org.defendev.easygo.domain.iam.repository.OwnershipUnitRepo;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.defendev.common.stream.Streams.stream;



@Service
public class QueryAnonymousPrivilegeService {

    private static final Logger log = LogManager.getLogger();

    private final OwnershipUnitRepo ownershipUnitRepo;

    public QueryAnonymousPrivilegeService(OwnershipUnitRepo ownershipUnitRepo) {
        this.ownershipUnitRepo = ownershipUnitRepo;
    }

    public Map<Privilege, Set<Long>> queryPrivilegeToOwnershipUnit() {
        return this.ownershipUnitRepo.findWithAnyAnonymousPrivilege().stream().flatMap(
            ownershipUnit -> stream(ownershipUnit.getAnonymousPrivilege())
                .map(privilege -> Map.entry(privilege, ownershipUnit.getId()))
        ).collect(Collectors.groupingBy(
            entry -> entry.getKey(),
            Collectors.mapping(entry -> entry.getValue(), Collectors.toSet())
        ));
    }

}
