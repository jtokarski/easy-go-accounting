package org.defendev.easygo.domain.fa.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.defendev.common.domain.query.filter.Filter;
import org.defendev.easygo.domain.fa.model.SourceDocument;
import org.defendev.easygo.domain.fa.model.SourceDocument_;
import org.defendev.easygo.domain.fa.service.query.SourceDocumentQuery;
import org.springframework.data.jpa.domain.Specification;

import static org.defendev.common.stream.Streams.stream;



public class SourceDocumentPredicateSpec implements Specification<SourceDocument> {

    private final SourceDocumentQuery query;

    public SourceDocumentPredicateSpec(SourceDocumentQuery query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<SourceDocument> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        final String searchPhrase = query.getSearchPhrase();
        if (StringUtils.isNotBlank(searchPhrase)) {
            final String searchPhraseLower = "%" + searchPhrase.toLowerCase() + "%";
            final Predicate likeSearchPhrase = criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get(SourceDocument_.controlNumber)), searchPhraseLower),
                criteriaBuilder.like(criteriaBuilder.lower(root.get(SourceDocument_.description)), searchPhraseLower)
            );
            predicate = criteriaBuilder.and(predicate, likeSearchPhrase);
        }

        final Filter filter = query.getFilter();
        stream(filter.getNumberPropertyFilters());

        return predicate;
    }

}
