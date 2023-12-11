package org.defendev.easygo.domain.iam.service.dto;

import org.defendev.common.domain.iam.IDefendevUserDetails;
import org.defendev.common.domain.iam.Privilege;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.defendev.common.stream.Streams.stream;



public class EasygoOidcUser extends DefaultOidcUser implements IDefendevUserDetails {

    private Map<Privilege, Set<Long>> privilegeToOwnershipUnit;

    public EasygoOidcUser(OidcIdToken idToken, OidcUserInfo userInfo,
                          Set<? extends GrantedAuthority> grantedAuthorities,
                          Map<Privilege, Set<Long>> privilegeToOwnershipUnit) {
        super(grantedAuthorities, idToken, userInfo);
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
        return stream(getAuthorities()).map(GrantedAuthority::toString).collect(Collectors.toSet());
    }

    @Override
    public Map<Privilege, Set<Long>> getPrivilegeToOwnershipUnit() {
        return privilegeToOwnershipUnit;
    }

}
