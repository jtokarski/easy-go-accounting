package org.defendev.easygo.web;

import org.defendev.common.domain.query.QueryOwnedBy;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.nonNull;


public class QueryOwnedByEmptyValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return QueryOwnedBy.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final QueryOwnedBy query = (QueryOwnedBy) target;
        if (nonNull(query.getOwnershipUnitExternalIds())) {
            errors.reject("nonNullOwnershipUnitExternalIds", "OwnershipUnitExternalIds not accepted in this endpoint");
        }
    }

}
