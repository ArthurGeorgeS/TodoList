package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa uma tarefa na lista de tarefas.
 */
public class Tarefa {

    private static int contadorId = 1;

    private final int id;
    private String titulo;
    private boolean concluida;
    private final LocalDateTime criadaEm;

    private static final DateTimeFormatter FORMATADOR =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Tarefa(String titulo) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.concluida = false;
        this.criadaEm = LocalDateTime.now();
    }

    // Construtor usado ao carregar do arquivo (com id e data já definidos)
    public Tarefa(int id, String titulo, boolean concluida, LocalDateTime criadaEm) {
        this.id = id;
        this.titulo = titulo;
        this.concluida = concluida;
        this.criadaEm = criadaEm;
        if (id >= contadorId) contadorId = id + 1;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public boolean isConcluida() { return concluida; }
    public LocalDateTime getCriadaEm() { return criadaEm; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setConcluida(boolean concluida) { this.concluida = concluida; }

    public String getDataFormatada() {
        return criadaEm.format(FORMATADOR);
    }

    @Override
    public String toString() {
        String status = concluida ? "[X]" : "[ ]";
        return String.format("#%d %s %s  (criada em: %s)", id, status, titulo, getDataFormatada());
    }
}
