package org.defendev.easygo.domain.useridentity.service.dto;

import org.defendev.easygo.domain.useridentity.api.Privilege;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.Collection;
import java.util.Map;
import java.util.Set;



public class EasygoOidcUser extends DefaultOidcUser implements IEasygoUserDetails {

    private Map<Privilege, Set<Long>> privilegeToOwnershipUnit;

    public EasygoOidcUser(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken,
                          OidcUserInfo userInfo, Map<Privilege, Set<Long>> privilegeToOwnershipUnit) {
        super(authorities, idToken, userInfo);
        this.privilegeToOwnershipUnit = privilegeToOwnershipUnit;
    }

    @Override
    public String getUsername() {
        return getPreferredUsername();
    }

    @Override
    public Map<Privilege, Set<Long>> getPrivilegeToOwnershipUnit() {
        return privilegeToOwnershipUnit;
    }

}
