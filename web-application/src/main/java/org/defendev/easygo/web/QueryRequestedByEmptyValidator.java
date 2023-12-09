package org.defendev.easygo.web;

import org.defendev.common.domain.query.QueryRequestedBy;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.nonNull;


public class QueryRequestedByEmptyValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return QueryRequestedBy.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final QueryRequestedBy query = (QueryRequestedBy) target;
        if (nonNull(query.getRequestedBy())) {
            errors.reject("nonNullRequestedBy", "RequestedBy not accepted in this endpoint");
        }
    }

}
