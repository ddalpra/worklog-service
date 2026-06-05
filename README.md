Worklog Service

This small service creates a daily worklog for support users by calling the existing ticket-service to determine closed tickets.

Run:

```bash
cd worklog-service
mvn spring-boot:run
```

Configuration in `src/main/resources/application.properties`.
