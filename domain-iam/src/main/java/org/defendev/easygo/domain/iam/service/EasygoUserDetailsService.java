package org.defendev.easygo.domain.iam.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.easygo.domain.iam.model.UserIdentity;
import org.defendev.easygo.domain.iam.repository.UserIdentityRepo;
import org.defendev.easygo.domain.iam.service.dto.EasygoUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class EasygoUserDetailsService implements UserDetailsService {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserIdentityRepo userIdentityRepo;

    @Transactional(transactionManager = "iamJpaTransactionManager")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserIdentity userIdentity = userIdentityRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new EasygoUserDetails(userIdentity.getId(), userIdentity.getUsername(), userIdentity.getPassword(),
            userIdentity.getPrivilegeToOwnershipUnit());
    }
}
