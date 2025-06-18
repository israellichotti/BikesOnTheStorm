import org.example.BicicletaDAO;
import org.example.Tela;

import javax.swing.*;
import java.sql.*;



public class Main {
    private static final String DB_URL = "jdbc:sqlite:bicicletas.db";
    private static Connection conn;
    private static JFrame frame;

    public static void main(String[] args) {
        conectarBancoDeDados();
        frame = new JFrame("Bikes on the Storm");
        BicicletaDAO bicicletaDAO = new BicicletaDAO(conn, frame);
        bicicletaDAO.criarTabelaSeNaoExiste();
        Tela tela = new Tela(frame, bicicletaDAO);
        SwingUtilities.invokeLater(() -> {
            tela.mostrarMainMenu();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void conectarBancoDeDados() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage());
            System.exit(1);
        }
    }
}