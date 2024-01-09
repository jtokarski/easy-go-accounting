package org.defendev.easygo.domain.iam.service.dto;

import org.defendev.common.domain.iam.IDefendevUserDetails;
import org.defendev.common.domain.iam.Privilege;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.defendev.common.stream.Streams.stream;



public class EasygoOAuth2User implements IDefendevUserDetails, OAuth2User {

    private final OAuth2User oAuth2User;

    private final Set<? extends GrantedAuthority> grantedAuthorities;

    private final Map<Privilege, Set<Long>> privilegeToOwnershipUnit;


    public EasygoOAuth2User(
            OAuth2User oAuth2User,
            Set<? extends GrantedAuthority> grantedAuthorities,
            Map<Privilege, Set<Long>> privilegeToOwnershipUnit
    ) {
        this.oAuth2User = oAuth2User;
        this.grantedAuthorities = grantedAuthorities;
        this.privilegeToOwnershipUnit = privilegeToOwnershipUnit;
    }


    @Override
    public String getUsername() {
        return oAuth2User.getName();
    }

    @Override
    public Set<String> getRoles() {
        return stream(grantedAuthorities).map(GrantedAuthority::toString).collect(Collectors.toSet());
    }

    @Override
    public Map<Privilege, Set<Long>> getPrivilegeToOwnershipUnit() {
        return privilegeToOwnershipUnit;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }
}
