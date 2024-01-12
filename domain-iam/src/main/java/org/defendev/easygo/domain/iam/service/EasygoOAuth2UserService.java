package org.defendev.easygo.domain.iam.service;

import org.defendev.common.domain.command.result.CommandResult;
import org.defendev.common.domain.iam.Privilege;
import org.defendev.easygo.domain.iam.api.IEasygoOAuth2UserService;
import org.defendev.easygo.domain.iam.model.UserIdentity;
import org.defendev.easygo.domain.iam.repository.UserIdentityRepo;
import org.defendev.easygo.domain.iam.service.command.RegisterOAuthUserIdentityCommand;
import org.defendev.easygo.domain.iam.service.dto.EasygoOAuth2User;
import org.defendev.easygo.domain.iam.service.dto.EasygoRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Component
public class EasygoOAuth2UserService extends DefaultOAuth2UserService implements IEasygoOAuth2UserService {

    private final UserIdentityRepo userIdentityRepo;

    private final IRegisterOAuthUserIdentityService registerOAuthUserIdentityService;

    @Autowired
    public EasygoOAuth2UserService(
        UserIdentityRepo userIdentityRepo,
        IRegisterOAuthUserIdentityService registerOAuthUserIdentityService
    ) {
        this.userIdentityRepo = userIdentityRepo;
        this.registerOAuthUserIdentityService = registerOAuthUserIdentityService;
    }

    @Transactional(transactionManager = "iamJpaTransactionManager")
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User oAuth2User = super.loadUser(userRequest);
        final String provider = userRequest.getClientRegistration().getRegistrationId();

        final Map<Privilege, Set<Long>> privilegeToOwnershipUnit =
                findOrRegisterOAuthUserIdentity(provider, oAuth2User.getName())
                        .map(UserIdentity::getPrivilegeToOwnershipUnit)
                        .orElseGet(Map::of);

        return new EasygoOAuth2User(oAuth2User, Set.of(EasygoRoles.fullyAuthenticated()), privilegeToOwnershipUnit);
    }

    private Optional<UserIdentity> findOrRegisterOAuthUserIdentity(String oauthProvider, String oauthUsername) {
        final Optional<UserIdentity> userIdentityOptional = userIdentityRepo.findOAuth(oauthProvider, oauthUsername);
        if (userIdentityOptional.isPresent()) {
            return userIdentityOptional;
        }
        final CommandResult<UserIdentity> result = registerOAuthUserIdentityService.execute(
            new RegisterOAuthUserIdentityCommand(oauthProvider, oauthUsername));
        if (result.isSuccess()) {
            final UserIdentity newOAuthUserIdentity = result.getData();
            return Optional.of(newOAuthUserIdentity);
        } else {
            return Optional.empty();
        }
    }

}
