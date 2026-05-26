package service;

import model.Tarefa;
import repository.TarefaRepositorio;

import java.util.List;
import java.util.Optional;

/**
 * Responsável por editar o título de uma tarefa existente pelo ID.
 */
public class EditarTarefa {

    private final List<Tarefa> tarefas;
    private final TarefaRepositorio repositorio;

    public EditarTarefa(List<Tarefa> tarefas, TarefaRepositorio repositorio) {
        this.tarefas = tarefas;
        this.repositorio = repositorio;
    }

    public boolean executar(int id, String novoTitulo) {
        Optional<Tarefa> encontrada = tarefas.stream()
                .filter(t -> t.getId() == id)
                .findFirst();

        if (encontrada.isEmpty()) return false;

        encontrada.get().setTitulo(novoTitulo.trim());
        repositorio.salvarTodas(tarefas);
        return true;
    }
}
