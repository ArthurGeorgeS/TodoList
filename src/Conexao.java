import java.sql.*;

public class Conexao {

    private static final String URL     = "jdbc:mysql://localhost:3306/blend_lista";
    private static final String USUARIO = "root";
    private static final String SENHA   = "";  // coloque a senha que funcionou no Workbench

    public static void main(String[] args) {
        listar();
    }

    public static void listar() {
        String sql = "SELECT id, nome FROM Tarefas";

        try (
                Connection conn        = DriverManager.getConnection(URL, USUARIO, SENHA);
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs           = stmt.executeQuery()
        ) {
            System.out.println("Banco cadastrado");

        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
        }
    }
}