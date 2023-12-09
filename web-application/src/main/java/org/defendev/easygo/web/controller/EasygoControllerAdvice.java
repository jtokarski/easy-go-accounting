package org.defendev.easygo.web.controller;

import org.defendev.easygo.web.QueryOwnedByEmptyValidator;
import org.defendev.easygo.web.QueryRequestedByEmptyValidator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;


@ControllerAdvice
public class EasygoControllerAdvice {

    @InitBinder
    public void initWebDataBinder(WebDataBinder binder) {
        binder.addValidators(new QueryRequestedByEmptyValidator(), new QueryOwnedByEmptyValidator());
    }

}
