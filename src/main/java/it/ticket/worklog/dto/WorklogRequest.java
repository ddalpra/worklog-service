package it.ticket.worklog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class WorklogRequest {
    @NotNull
    private LocalDate day;

    @Min(0)
    private double hoursSpent;

    public LocalDate getDay() { return day; }
    public void setDay(LocalDate day) { this.day = day; }

    public double getHoursSpent() { return hoursSpent; }
    public void setHoursSpent(double hoursSpent) { this.hoursSpent = hoursSpent; }
}
