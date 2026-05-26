package repository;

import model.Tarefa;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsável por salvar e carregar tarefas em arquivo (persistência).
 * Formato do arquivo: id|titulo|concluida|criadaEm
 */
public class TarefaRepositorio {

    private static final String ARQUIVO = "tarefas.dat";
    private static final String SEPARADOR = "\\|";
    private static final String SEP = "|";

    public List<Tarefa> carregarTodas() {
        List<Tarefa> tarefas = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) return tarefas;

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                Tarefa tarefa = parsearLinha(linha);
                if (tarefa != null) tarefas.add(tarefa);
            }
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível carregar os dados salvos.");
        }

        return tarefas;
    }

    public void salvarTodas(List<Tarefa> tarefas) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Tarefa tarefa : tarefas) {
                escritor.write(formatarLinha(tarefa));
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível salvar os dados.");
        }
    }

    private String formatarLinha(Tarefa tarefa) {
        return tarefa.getId() + SEP
                + tarefa.getTitulo() + SEP
                + tarefa.isConcluida() + SEP
                + tarefa.getCriadaEm().toString();
    }

    private Tarefa parsearLinha(String linha) {
        try {
            String[] partes = linha.split(SEPARADOR, 4);
            if (partes.length < 4) return null;

            int id = Integer.parseInt(partes[0].trim());
            String titulo = partes[1].trim();
            boolean concluida = Boolean.parseBoolean(partes[2].trim());
            LocalDateTime criadaEm = LocalDateTime.parse(partes[3].trim());

            return new Tarefa(id, titulo, concluida, criadaEm);
        } catch (Exception e) {
            return null; // linha corrompida é ignorada com segurança
        }
    }
}
