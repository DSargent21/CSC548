# CSC548 Assignment 3

Diego Sargent

| Task | Project | Endpoint | Screenshot |
| --- | --- | --- | --- |
| 1 | `task1-dev-tools` | `/` | `screenshots/task1.png` |
| 2 | `task2-actuator` | `/actuator/info` | `screenshots/task2.png` |
| 3 | `task3-constructor-injection` | `/basketball` | `screenshots/task3.png` |
| 4 | `task4-form-data` | `/showForm` | `screenshots/task4.png` |
| 5 | `task5-model` | `/showForm` | `screenshots/task5.png` |

## Run

Each project is a standalone Spring Boot app:

```bash
cd task1-dev-tools
./mvnw spring-boot:run
```

Then open `http://localhost:8080`.

Only one project runs at a time because they all use port 8080.
