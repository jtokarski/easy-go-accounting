package org.defendev.easygo.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(path = ApiBaseController.API_PATH)
public class ApiBaseController {

    public static final String API_PATH = "api";

    public static final String EXTERNAL_ID_PATH_SEGMENT = "{externalId}";

    public static final String BROWSE_PATH_SEGMENT = "_browse";

}
