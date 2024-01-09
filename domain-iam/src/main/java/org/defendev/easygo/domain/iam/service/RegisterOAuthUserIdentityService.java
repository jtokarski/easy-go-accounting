package org.defendev.easygo.domain.iam.service;

import org.defendev.common.domain.command.result.CommandResult;
import org.defendev.easygo.domain.iam.model.UserIdentity;
import org.defendev.easygo.domain.iam.repository.UserIdentityRepo;
import org.defendev.easygo.domain.iam.service.command.RegisterOAuthUserIdentityCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegisterOAuthUserIdentityService implements IRegisterOAuthUserIdentityService {

    private final UserIdentityRepo userIdentityRepo;

    @Autowired
    public RegisterOAuthUserIdentityService(UserIdentityRepo userIdentityRepo) {
        this.userIdentityRepo = userIdentityRepo;
    }

    @Override
    public CommandResult<UserIdentity> execute(RegisterOAuthUserIdentityCommand command) {
        final UserIdentity userIdentity = new UserIdentity();
        userIdentity.setOauthProvider(command.getOauthProvider());
        userIdentity.setOauthUsername(command.getOauthUsername());
        userIdentityRepo.save(userIdentity);
        return CommandResult.success(userIdentity);
    }

}
