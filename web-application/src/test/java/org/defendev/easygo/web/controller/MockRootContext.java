package org.defendev.easygo.web.controller;

import org.defendev.common.domain.query.result.QueryResult;
import org.defendev.common.domain.resrep.CollectionMeta;
import org.defendev.common.domain.resrep.ICollectionMeta;
import org.defendev.easygo.domain.fa.api.DocumentCollectionResRep;
import org.defendev.easygo.domain.fa.api.IFindDocumentService;
import org.defendev.easygo.domain.fa.api.IQueryDocumentService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class MockRootContext {

    @Bean
    public OidcUserService mockOidcUserService() {
        return Mockito.mock(OidcUserService.class);
    }

    @Bean
    public UserDetailsService mockUserDetailsService() {
        return Mockito.mock(UserDetailsService.class);
    }

    @Bean
    public IFindDocumentService mockFindDocumentService() {
        return Mockito.mock(IFindDocumentService.class);
    }

    @Bean
    public IQueryDocumentService mockQueryDocumentService() {
        final IQueryDocumentService mock = Mockito.mock(IQueryDocumentService.class);
        when(mock.execute(any())).thenReturn(
            QueryResult.success(new DocumentCollectionResRep(
                new CollectionMeta(ICollectionMeta.NO_SUCH_PAGE, 0, ICollectionMeta.TOTAL_ELEMENTS_UNKNOWN),
                List.of()
            ))
        );
        return mock;
    }

}
