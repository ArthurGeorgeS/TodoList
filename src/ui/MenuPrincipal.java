package ui;

import model.Tarefa;
import service.TarefaService;

import java.util.List;
import java.util.Scanner;

/**
 * Interface de linha de comando.
 * Responsável apenas por exibir informações e capturar entrada do usuário.
 */
public class MenuPrincipal {

    private final TarefaService service;
    private final Scanner scanner;

    public MenuPrincipal(TarefaService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        exibirBoasVindas();
        boolean rodando = true;

        while (rodando) {
            exibirMenu();
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1" -> adicionarTarefa();
                case "2" -> listarTarefas("todas");
                case "3" -> marcarConcluida();
                case "4" -> removerTarefa();
                case "5" -> editarTarefa();
                case "6" -> listarTarefas("pendentes");
                case "7" -> listarTarefas("concluidas");
                case "0" -> rodando = false;
                default  -> System.out.println("\nOpção inválida. Tente novamente.");
            }
        }

        System.out.println("\nAté logo!");
        scanner.close();
    }

    private void exibirBoasVindas() {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║       TODO LIST — SENAI      ║");
        System.out.println("╚══════════════════════════════╝");
    }

    private void exibirMenu() {
        System.out.println("\n─── Menu ───────────────────────");
        System.out.println(" 1. Adicionar tarefa");
        System.out.println(" 2. Listar todas as tarefas");
        System.out.println(" 3. Marcar tarefa como concluída");
        System.out.println(" 4. Remover tarefa");
        System.out.println(" 5. Editar tarefa");
        System.out.println(" 6. Listar pendentes");
        System.out.println(" 7. Listar concluídas");
        System.out.println(" 0. Sair");
        System.out.println("────────────────────────────────");
        System.out.print("Escolha: ");
    }

    private void adicionarTarefa() {
        System.out.print("\nTítulo da tarefa: ");
        String titulo = scanner.nextLine().trim();

        if (titulo.isEmpty()) {
            System.out.println("O título não pode ser vazio.");
            return;
        }

        service.adicionarTarefa(titulo);
        System.out.println("Tarefa adicionada com sucesso!");
    }

    private void listarTarefas(String filtro) {
        List<Tarefa> lista = switch (filtro) {
            case "pendentes"  -> service.listarPendentes();
            case "concluidas" -> service.listarConcluidas();
            default           -> service.listarTodas();
        };

        String titulo = switch (filtro) {
            case "pendentes"  -> "Tarefas Pendentes";
            case "concluidas" -> "Tarefas Concluídas";
            default           -> "Todas as Tarefas";
        };

        System.out.println("\n─── " + titulo + " (" + lista.size() + ") ───");

        if (lista.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }

        lista.forEach(System.out::println);
    }

    private void marcarConcluida() {
        listarTarefas("pendentes");
        System.out.print("\nID da tarefa a concluir: ");
        int id = lerInteiro();
        if (id < 0) return;

        boolean sucesso = service.marcarComoConcluida(id);
        System.out.println(sucesso ? "Tarefa marcada como concluída!" : "Tarefa não encontrada.");
    }

    private void removerTarefa() {
        listarTarefas("todas");
        System.out.print("\nID da tarefa a remover: ");
        int id = lerInteiro();
        if (id < 0) return;

        boolean sucesso = service.removerTarefa(id);
        System.out.println(sucesso ? "Tarefa removida com sucesso!" : "Tarefa não encontrada.");
    }

    private void editarTarefa() {
        listarTarefas("todas");
        System.out.print("\nID da tarefa a editar: ");
        int id = lerInteiro();
        if (id < 0) return;

        System.out.print("Novo título: ");
        String novoTitulo = scanner.nextLine().trim();

        if (novoTitulo.isEmpty()) {
            System.out.println("O título não pode ser vazio.");
            return;
        }

        boolean sucesso = service.editarTitulo(id, novoTitulo);
        System.out.println(sucesso ? "Tarefa editada com sucesso!" : "Tarefa não encontrada.");
    }

    private int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return -1;
        }
    }
}
