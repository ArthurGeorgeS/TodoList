import service.TarefaService;
import ui.MenuPrincipal;

/**
 * Ponto de entrada da aplicação TodoList.
 * Inicializa o serviço e abre o menu principal.
 */
public class Main {

    public static void main(String[] args) {
        TarefaService service = new TarefaService();
        MenuPrincipal menu = new MenuPrincipal(service);
        menu.iniciar();
    }
}
