package org.defendev.easygo.domain.fa.repository;


import org.defendev.common.domain.query.sort.SortOrder;
import org.defendev.common.spring6.data.jpa.domain.PageableSpecification;
import org.defendev.easygo.domain.fa.api.SourceDocumentQuery;
import org.defendev.easygo.domain.fa.model.SourceDocument_;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;

import java.util.List;
import java.util.stream.Collectors;

import static org.defendev.common.stream.Streams.stream;


public class SourceDocumentPageableSpec extends PageableSpecification<SourceDocumentQuery> {

    public SourceDocumentPageableSpec(SourceDocumentQuery query) {
        super(query);
    }

    @Override
    public JpaSort getDefaultSort() {
        return JpaSort.of(Sort.Direction.ASC, SourceDocument_.id);
    }

    @Override
    public Pageable toPageable() {
        final int pageNumber = query.getPageNumber();
        final int pageSize = query.getPageSize();

        final List<JpaSort> sorts = stream(query.getSortOrders()).map((SortOrder sortOrder) -> {
            final Direction direction = PageableSpecification.TO_SPRINGDATA_DIRECTION.get(sortOrder.getDirection());
            switch (sortOrder.getProperty()) {
                case "controlNumber":
                    return JpaSort.of(direction, SourceDocument_.controlNumber);
                default:
                    throw new UnsupportedOperationException("Unsupported sort property");
            }
        }).collect(Collectors.toList());
        final Sort sortConjunct = conjunctWithDefaultSort(sorts);
        return PageRequest.of(pageNumber, pageSize, sortConjunct);
    }

}
