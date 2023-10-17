package org.defendev.easygo.domain.fa.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.defendev.common.domain.query.Query;
import org.defendev.common.domain.query.QueryFilter;
import org.defendev.common.domain.query.QueryOwnedBy;
import org.defendev.common.domain.query.QueryPageable;
import org.defendev.common.domain.query.QuerySearchPhrase;
import org.defendev.common.domain.query.QuerySort;
import org.defendev.common.domain.query.filter.Filter;
import org.defendev.common.domain.query.sort.SortOrder;

import java.util.List;
import java.util.Set;

import static java.util.Objects.nonNull;



public class DocumentQuery extends Query
    implements QueryPageable, QuerySearchPhrase, QueryFilter, QuerySort, QueryOwnedBy {

    private final int pageNumber;

    private final int pageSize;

    private final String searchPhrase;

    private final List<SortOrder> sortOrders;

    private final Filter filter;

    private final boolean resolveOwnershipUnitsForRequestingUser;

    private final Set<String> ownershipUnitExternalIds;

    private final Set<Long> ownershipUnitIds;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DocumentQuery(
        @JsonProperty("pageNumber") Integer pageNumber,
        @JsonProperty("pageSize") Integer pageSize,
        @JsonProperty("searchPhrase") String searchPhrase,
        @JsonProperty("sortOrders") List<SortOrder> sortOrders,
        @JsonProperty("filter") Filter filter,
        @JsonProperty("resolveOwnershipUnitsForRequestingUser") boolean resolveOwnershipUnitsForRequestingUser,
        @JsonProperty("ownershipUnitExternalIds") Set<String> ownershipUnitExternalIds
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
        this.resolveOwnershipUnitsForRequestingUser = resolveOwnershipUnitsForRequestingUser;
        this.ownershipUnitExternalIds = ownershipUnitExternalIds;
        this.ownershipUnitIds = Set.of();
    }

    private DocumentQuery(int pageNumber, int pageSize, String searchPhrase, List<SortOrder> sortOrders,
                          Filter filter, boolean resolveOwnershipUnitsForRequestingUser,
                          Set<String> ownershipUnitExternalIds, Set<Long> ownershipUnitIds) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.searchPhrase = searchPhrase;
        this.sortOrders = sortOrders;
        this.filter = filter;
        this.resolveOwnershipUnitsForRequestingUser = resolveOwnershipUnitsForRequestingUser;
        this.ownershipUnitExternalIds = ownershipUnitExternalIds;
        this.ownershipUnitIds = ownershipUnitIds;
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

    @Override
    public boolean getResolveOwnershipUnitsForRequestingUser() {
        return resolveOwnershipUnitsForRequestingUser;
    }

    @Override
    public Set<String> getOwnershipUnitExternalIds() {
        return ownershipUnitExternalIds;
    }

    public Set<Long> getOwnershipUnitIds() {
        return ownershipUnitIds;
    }

    public DocumentQuery withOwnershipUnitIds(Set<Long> ownershipUnitIds) {
        return new DocumentQuery(pageNumber, pageSize, searchPhrase, sortOrders, filter,
                resolveOwnershipUnitsForRequestingUser, ownershipUnitExternalIds, ownershipUnitIds);
    }
}
