package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Representa uma tarefa na lista de tarefas.
 */
public class Task {

    private final String id;
    private String title;
    private boolean completed;
    private final LocalDateTime createdAt;

    public Task(String title) {
        this.id        = UUID.randomUUID().toString();
        this.title     = title;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }

    // Construtor usado ao carregar do arquivo
    public Task(String id, String title, boolean completed, LocalDateTime createdAt) {
        this.id        = id;
        this.title     = title;
        this.completed = completed;
        this.createdAt = createdAt;
    }

    public String getId()          { return id; }
    public String getTitle()       { return title; }
    public boolean isCompleted()   { return completed; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setTitle(String title)        { this.title = title; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public String getFormattedDate() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return createdAt.format(fmt);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | %s",
            completed ? "X" : " ", title,
            getFormattedDate(), id);
    }
}
