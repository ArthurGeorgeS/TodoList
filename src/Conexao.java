
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/blend_lista?disabledAuthenticationPlugins=auth_gssapi_client";
    private static final String USUARIO = "root";
    private static final String SENHA = "";  // coloque a senha que funcionou no Workbench

    public static void main(String[] args) {
        listar();
    }

    public static void listar() {
        String sql = "SELECT id, nome FROM Tarefas";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado: " + e.getMessage());
            return;
        }

        try (
                Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            System.out.println("Conectado ao banco: " + URL);
            int count = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                System.out.println(id + ": " + nome);
                count++;
            }
            if (count == 0) {
                System.out.println("Nenhuma tarefa encontrada.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
