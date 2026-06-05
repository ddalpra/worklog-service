package it.ticket.worklog.repository;

import it.ticket.worklog.model.Worklog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface WorklogRepository extends JpaRepository<Worklog, UUID> {
    Optional<Worklog> findByUsernameAndDay(String username, LocalDate day);
}
