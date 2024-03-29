package org.defendev.easygo.domain.fa.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.defendev.common.domain.query.filter.Filter;
import org.defendev.easygo.domain.fa.api.DocumentQuery;
import org.defendev.easygo.domain.fa.model.Document;
import org.defendev.easygo.domain.fa.model.Document_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

import static java.util.Objects.isNull;
import static org.defendev.common.stream.Streams.stream;



public class DocumentPredicateSpec implements Specification<Document> {

    private final DocumentQuery query;

    public DocumentPredicateSpec(DocumentQuery query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<Document> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        final Set<Long> ownershipUnitIds = query.getOwnershipUnitIds();
        if (isNull(ownershipUnitIds)) {
            throw new IllegalStateException("Ownership Unit Ids have to be specified");
        }
        predicate = criteriaBuilder.and(predicate, root.get(Document_.ownershipUnitId).in(ownershipUnitIds));

        final String searchPhrase = query.getSearchPhrase();
        if (StringUtils.isNotBlank(searchPhrase)) {
            final String searchPhraseLower = "%" + searchPhrase.toLowerCase() + "%";
            final Predicate likeSearchPhrase = criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Document_.controlNumber)), searchPhraseLower),
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Document_.description)), searchPhraseLower)
            );
            predicate = criteriaBuilder.and(predicate, likeSearchPhrase);
        }

        final Filter filter = query.getFilter();
        stream(filter.getNumberPropertyFilters());

        return predicate;
    }

}
