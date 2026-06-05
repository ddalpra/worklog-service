package it.ticket.worklog.controller;

import it.ticket.worklog.dto.WorklogRequest;
import it.ticket.worklog.model.Worklog;
import it.ticket.worklog.service.WorklogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/worklogs")
public class WorklogController {
    private final WorklogService svc;

    public WorklogController(WorklogService svc) { this.svc = svc; }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_support_l1','ROLE_support_l2','ROLE_supervisor')")
    public ResponseEntity<Worklog> create(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody WorklogRequest req) {
        String username = jwt.getClaimAsString("preferred_username");
        String token = "Bearer " + jwt.getTokenValue();
        Worklog w = svc.createDailyWorklog(username, req, token);
        return ResponseEntity.status(201).body(w);
    }
}
