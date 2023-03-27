package org.defendev.easygo.domain.fa.service.query;

import java.util.List;
import static java.util.Objects.nonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.defendev.common.domain.query.Query;
import org.defendev.common.domain.query.QueryFilter;
import org.defendev.common.domain.query.QueryPageable;
import org.defendev.common.domain.query.QuerySearchPhrase;
import org.defendev.common.domain.query.QuerySort;
import org.defendev.common.domain.query.filter.Filter;
import org.defendev.common.domain.query.sort.SortOrder;



public class SourceDocumentQuery extends Query implements QueryPageable, QuerySearchPhrase, QueryFilter, QuerySort {

    private final int pageNumber;

    private final int pageSize;

    private final String searchPhrase;

    private final List<SortOrder> sortOrders;

    private final Filter filter;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SourceDocumentQuery(
        @JsonProperty("pageNumber") Integer pageNumber,
        @JsonProperty("pageSize") Integer pageSize,
        @JsonProperty("searchPhrase") String searchPhrase,
        @JsonProperty("sortOrders") List<SortOrder> sortOrders,
        @JsonProperty("filter") Filter filter
    ) {
        if (nonNull(pageNumber) && nonNull(pageSize)) {
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
        } else {
            this.pageNumber = 0;
            this.pageSize = 10;
        }
        this.searchPhrase = searchPhrase;
        this.sortOrders = sortOrders;
        this.filter = filter;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public String getSearchPhrase() {
        return searchPhrase;
    }

    @Override
    public List<SortOrder> getSortOrders() {
        return sortOrders;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
}
