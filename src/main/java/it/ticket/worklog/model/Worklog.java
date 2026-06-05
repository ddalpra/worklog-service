package it.ticket.worklog.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "worklogs")
public class Worklog {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDate day;

    @Column(nullable = false)
    private int closedTicketsCount;

    @Column(nullable = false)
    private double hoursSpent;

    @Column(columnDefinition = "text")
    private String tickets; // JSON array or comma separated list of ticket ids

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public LocalDate getDay() { return day; }
    public void setDay(LocalDate day) { this.day = day; }

    public int getClosedTicketsCount() { return closedTicketsCount; }
    public void setClosedTicketsCount(int closedTicketsCount) { this.closedTicketsCount = closedTicketsCount; }

    public double getHoursSpent() { return hoursSpent; }
    public void setHoursSpent(double hoursSpent) { this.hoursSpent = hoursSpent; }

    public String getTickets() { return tickets; }
    public void setTickets(String tickets) { this.tickets = tickets; }
}
