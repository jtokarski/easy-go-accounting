package org.defendev.easygo.domain.iam.api;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;


public interface IEasygoOAuth2UserService extends OAuth2UserService<OAuth2UserRequest, OAuth2User> { }
