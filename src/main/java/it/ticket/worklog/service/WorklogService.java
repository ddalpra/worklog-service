package it.ticket.worklog.service;

import it.ticket.worklog.dto.WorklogRequest;
import it.ticket.worklog.model.Worklog;
import it.ticket.worklog.repository.WorklogRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorklogService {
    private final WorklogRepository repo;
    private final WebClient webClient;

    public WorklogService(WorklogRepository repo, WebClient.Builder webClientBuilder) {
        this.repo = repo;
        this.webClient = webClientBuilder.baseUrl("http://localhost:3000").build();
    }

    public Worklog createDailyWorklog(String username, WorklogRequest req, String bearerToken) {
        LocalDate day = req.getDay();
        // Call ticket-service to get user's tickets (mine)
        List<?> tickets = webClient.get()
                .uri("/api/v1/tickets/mine")
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .retrieve()
                .bodyToMono(List.class)
                .block();

        // Filter: tickets may include `state` (EN) with value "CLOSED" or `state_job` (IT) with value "CHIUSA".
        int closedCount = 0;
        StringBuilder ids = new StringBuilder();
        if (tickets != null) {
            for (Object t : tickets) {
                try {
                    // map to LinkedHashMap
                    var map = (java.util.Map) t;
                    Object state = map.get("state");
                    Object stateJob = map.get("state_job");
                    boolean isClosed = (state != null && "CLOSED".equals(state.toString()))
                            || (stateJob != null && "CHIUSA".equals(stateJob.toString()));
                    if (isClosed) {
                        closedCount++;
                        Object id = map.get("id");
                        if (id != null) ids.append(id.toString()).append(',');
                    }
                } catch (Exception ignored) {
                }
            }
        }

        // Upsert: se esiste un worklog per lo stesso user e giorno, aggiorna i campi
        Worklog w = repo.findByUsernameAndDay(username, day).orElseGet(Worklog::new);
        w.setUsername(username);
        w.setDay(day);
        w.setClosedTicketsCount(closedCount);
        w.setHoursSpent(req.getHoursSpent());
        if (ids.length() > 0) ids.setLength(ids.length() - 1);
        w.setTickets(ids.toString());

        return repo.save(w);
    }
}
