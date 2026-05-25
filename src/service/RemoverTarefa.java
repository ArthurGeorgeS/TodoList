package service;

import model.Tarefa;
import repository.TarefaRepositorio;

import java.util.List;

/**
 * Responsável por remover uma tarefa da lista pelo ID.
 */
public class RemoverTarefa {

    private final List<Tarefa> tarefas;
    private final TarefaRepositorio repositorio;

    public RemoverTarefa(List<Tarefa> tarefas, TarefaRepositorio repositorio) {
        this.tarefas = tarefas;
        this.repositorio = repositorio;
    }

    public boolean executar(int id) {
        boolean removida = tarefas.removeIf(t -> t.getId() == id);

        if (removida) {
            repositorio.salvarTodas(tarefas);
        }

        return removida;
    }
}
