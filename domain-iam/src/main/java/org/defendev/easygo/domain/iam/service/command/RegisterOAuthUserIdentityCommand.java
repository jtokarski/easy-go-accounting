package org.defendev.easygo.domain.iam.service.command;

import org.defendev.common.domain.command.Command;



public class RegisterOAuthUserIdentityCommand extends Command {

    final String oauthProvider;

    final String oauthUsername;

    public RegisterOAuthUserIdentityCommand(String oauthProvider, String oauthUsername) {
        this.oauthProvider = oauthProvider;
        this.oauthUsername = oauthUsername;
    }

    public String getOauthProvider() {
        return oauthProvider;
    }

    public String getOauthUsername() {
        return oauthUsername;
    }
}
