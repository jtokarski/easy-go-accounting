package org.defendev.easygo.web.controller;

import org.defendev.common.domain.query.result.QueryResult;
import org.defendev.common.domain.resrep.CollectionMeta;
import org.defendev.common.domain.resrep.ICollectionMeta;
import org.defendev.easygo.domain.fa.api.DocumentCollectionResRep;
import org.defendev.easygo.domain.fa.api.DocumentFullDto;
import org.defendev.easygo.domain.fa.api.IFindDocumentService;
import org.defendev.easygo.domain.fa.api.IQueryDocumentService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.List;

import static org.defendev.common.time.TimeUtil.ZULU_ZONE_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;



public class MockRootContext {

    @Bean
    public UserDetailsService mockUserDetailsService() {
        return Mockito.mock(UserDetailsService.class);
    }

    @Bean
    public IFindDocumentService mockFindDocumentService() {
        final IFindDocumentService mock = Mockito.mock(IFindDocumentService.class);
        final DocumentFullDto mockFullDto = new DocumentFullDto("1001", "CCC-1001",
            ZonedDateTime.of(LocalDate.of(2019, Month.APRIL, 1), LocalTime.of(14, 59), ZULU_ZONE_ID),
            "Document 1001");
        when(mock.execute(any())).thenReturn(QueryResult.success(mockFullDto));
        return mock;
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
