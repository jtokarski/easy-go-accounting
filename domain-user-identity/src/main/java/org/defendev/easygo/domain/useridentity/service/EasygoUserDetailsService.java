package org.defendev.easygo.domain.useridentity.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.easygo.domain.useridentity.model.UserIdentity;
import org.defendev.easygo.domain.useridentity.repository.UserIdentityRepo;
import org.defendev.easygo.domain.useridentity.service.dto.EasygoUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;



@Component
public class EasygoUserDetailsService implements UserDetailsService {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserIdentityRepo userIdentityRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userIdentityRepo.findByUsername(username).map((UserIdentity userIdentity) ->
            new EasygoUserDetails(userIdentity.getId(), userIdentity.getUsername(), userIdentity.getPassword())
        ).orElseThrow(
            () -> new UsernameNotFoundException("User not found")
        );
    }
}
