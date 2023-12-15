package org.defendev.easygo.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.defendev.common.time.ClockManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static java.util.Objects.nonNull;



@Controller
public class LastVisitedController extends ApiBaseController {

    private static final Logger log = LogManager.getLogger();

    private static final String LAST_VISITED_KEY = "lastVisited";

    private final ClockManager clockManager;

    @Autowired
    public LastVisitedController(ClockManager clockManager) {
        this.clockManager = clockManager;
    }

    @RequestMapping(path = {"last-visited"}, method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> getLastVisited(HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final ZonedDateTime savedLastVisited = (ZonedDateTime) session.getAttribute(LAST_VISITED_KEY);
        final ZonedDateTime now = ZonedDateTime.now(clockManager.clockUtc());
        session.setAttribute(LAST_VISITED_KEY, now);
        final Map<String, String> dto = Map.of(LAST_VISITED_KEY,
            nonNull(savedLastVisited) ? savedLastVisited.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : "-"
        );
        return ResponseEntity.ok(dto);
    }

}
