package service;

import model.Tarefa;
import repository.TarefaRepositorio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Coordena as operações sobre a lista de tarefas.
 * Delega remoção e edição para classes especializadas.
 */
public class TarefaService {

    private final List<Tarefa> tarefas;
    private final TarefaRepositorio repositorio;
    private final RemoverTarefa removerTarefa;
    private final EditarTarefa editarTarefa;

    public TarefaService() {
        this.repositorio = new TarefaRepositorio();
        this.tarefas = repositorio.carregarTodas();
        this.removerTarefa = new RemoverTarefa(tarefas, repositorio);
        this.editarTarefa = new EditarTarefa(tarefas, repositorio);
    }

    public void adicionarTarefa(String titulo) {
        Tarefa nova = new Tarefa(titulo.trim());
        tarefas.add(nova);
        repositorio.salvarTodas(tarefas);
    }

    public List<Tarefa> listarTodas() {
        return tarefas;
    }

    public List<Tarefa> listarPendentes() {
        return tarefas.stream()
                .filter(t -> !t.isConcluida())
                .collect(Collectors.toList());
    }

    public List<Tarefa> listarConcluidas() {
        return tarefas.stream()
                .filter(Tarefa::isConcluida)
                .collect(Collectors.toList());
    }

    public boolean marcarComoConcluida(int id) {
        Optional<Tarefa> encontrada = buscarPorId(id);
        if (encontrada.isEmpty()) return false;

        encontrada.get().setConcluida(true);
        repositorio.salvarTodas(tarefas);
        return true;
    }

    public boolean removerTarefa(int id) {
        return removerTarefa.executar(id);
    }

    public boolean editarTitulo(int id, String novoTitulo) {
        return editarTarefa.executar(id, novoTitulo);
    }

    private Optional<Tarefa> buscarPorId(int id) {
        return tarefas.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }
}
