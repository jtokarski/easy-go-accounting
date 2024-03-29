package org.defendev.easygo.domain.iam.service.dto;

import org.defendev.common.domain.iam.IDefendevUserDetails;
import org.defendev.common.domain.iam.Privilege;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.defendev.common.stream.Streams.stream;



public class EasygoOidcUser implements IDefendevUserDetails, OidcUser {

    private final OidcUser oidcUser;

    private final Set<? extends GrantedAuthority> grantedAuthorities;

    private final Map<Privilege, Set<Long>> privilegeToOwnershipUnit;

    public EasygoOidcUser(OidcUser oidcUser, Set<? extends GrantedAuthority> grantedAuthorities,
                          Map<Privilege, Set<Long>> privilegeToOwnershipUnit) {
        this.oidcUser = oidcUser;
        this.grantedAuthorities = grantedAuthorities;
        this.privilegeToOwnershipUnit = privilegeToOwnershipUnit;
    }

    @Override
    public String getUsername() {
        return isNotBlank(getPreferredUsername()) ?
            getPreferredUsername()
            : getEmail();
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
    public Map<String, Object> getClaims() {
        return oidcUser.getClaims();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return oidcUser.getUserInfo();
    }

    @Override
    public OidcIdToken getIdToken() {
        return oidcUser.getIdToken();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oidcUser.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getName() {
        return oidcUser.getName();
    }
}
