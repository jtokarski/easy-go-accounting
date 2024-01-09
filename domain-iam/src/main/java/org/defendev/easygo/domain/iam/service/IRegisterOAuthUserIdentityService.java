package org.defendev.easygo.domain.iam.service;

import org.defendev.common.domain.command.result.CommandResult;
import org.defendev.easygo.domain.iam.model.UserIdentity;
import org.defendev.easygo.domain.iam.service.command.RegisterOAuthUserIdentityCommand;



public interface IRegisterOAuthUserIdentityService {

    CommandResult<UserIdentity> execute(RegisterOAuthUserIdentityCommand command);

}
