import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Main {
    private static final String DB_URL = "jdbc:sqlite:bicicletas.db";
    private static final String GERENTE_SENHA = "123456";
    private static JFrame frame = new JFrame();
    private static Connection conn;

    private static String aluguelIdSelecionado = null;
    private static int aluguelDias = 0;

    public static void main(String[] args) {
        connectDatabase();
        createTableIfNeeded();
        SwingUtilities.invokeLater(Main::showMainMenu);
    }

    private static void connectDatabase() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados.");
            System.exit(1);
        }
    }

    private static void createTableIfNeeded() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS bicicletas (id TEXT PRIMARY KEY, modelo TEXT, marca TEXT, tamanhoDoAro TEXT, aluguelBase REAL, aluguelDia REAL, seEstaAlugada INTEGER)");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar tabela.");
            System.exit(1);
        }
    }

    // Método para tela de senha do gerente
    private static void showPasswordScreen() {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(3, 1));

        JPanel panelSenha = new JPanel();
        JLabel labelSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField(10);
        panelSenha.add(labelSenha);
        panelSenha.add(campoSenha);

        JPanel panelBotoes = new JPanel();
        JButton btnEntrar = new JButton("Entrar");
        JButton btnVoltar = new JButton("Voltar");
        panelBotoes.add(btnEntrar);
        panelBotoes.add(btnVoltar);

        btnEntrar.addActionListener(e -> {
            String senha = new String(campoSenha.getPassword());
            if (senha.equals(GERENTE_SENHA)) {
                showGerenciarBicicletas();
            } else {
                JOptionPane.showMessageDialog(frame, "Senha incorreta.");
            }
        });

        btnVoltar.addActionListener(e -> showMainMenu());

        frame.add(panelSenha);
        frame.add(panelBotoes);
        frame.revalidate();
        frame.repaint();
    }

    // Placeholder do método de gerenciamento (você pode adaptar conforme necessário)
    private static void showGerenciarBicicletas() {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(4, 1));

        JButton btnAdicionar = new JButton("Adicionar Bicicleta");
        JButton btnExcluir = new JButton("Excluir Bicicleta");
        JButton btnVoltar = new JButton("Voltar");

        btnAdicionar.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Tela de Adicionar ainda não implementada."));
        btnExcluir.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Tela de Excluir ainda não implementada."));
        btnVoltar.addActionListener(e -> showMainMenu());

        frame.add(btnAdicionar);
        frame.add(btnExcluir);
        frame.add(btnVoltar);
        frame.revalidate();
        frame.repaint();
    }

    // (demais métodos permanecem iguais...)

    private static void showDevolverBicicleta() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        JTextField txtId = new JTextField(5);
        JButton btnDevolver = new JButton("Devolver");
        JButton btnVoltar = new JButton("Voltar");

        panel.add(new JLabel("ID da bicicleta a devolver: "));
        panel.add(txtId);
        panel.add(btnDevolver);
        panel.add(btnVoltar);

        btnVoltar.addActionListener(e -> showMainMenu());

        btnDevolver.addActionListener(e -> {
            String id = txtId.getText();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Digite um ID válido.");
                return;
            }
            try {
                PreparedStatement psCheck = conn.prepareStatement("SELECT seEstaAlugada FROM bicicletas WHERE id = ?");
                psCheck.setString(1, id);
                ResultSet rs = psCheck.executeQuery();
                if (rs.next()) {
                    if (rs.getInt("seEstaAlugada") == 1) {
                        PreparedStatement ps = conn.prepareStatement("UPDATE bicicletas SET seEstaAlugada = 0 WHERE id = ?");
                        ps.setString(1, id);
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(frame, "Bicicleta devolvida com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Esta bicicleta já está disponível.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Bicicleta não encontrada.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao devolver bicicleta.");
            }
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private static void showMainMenu() {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(4, 1));

        JButton btnGerenciar = new JButton("Gerenciar Bicicletas");
        JButton btnAlugar = new JButton("Alugar Bicicletas");
        JButton btnListar = new JButton("Listar Bicicletas");
        JButton btnDevolver = new JButton("Devolver Bicicleta");

        btnGerenciar.addActionListener(e -> showPasswordScreen());
        btnAlugar.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Função Alugar ainda não implementada."));
        btnListar.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Função Listar ainda não implementada."));
        btnDevolver.addActionListener(e -> showDevolverBicicleta());

        frame.add(btnGerenciar);
        frame.add(btnAlugar);
        frame.add(btnListar);
        frame.add(btnDevolver);

        frame.setTitle("Menu Principal");
        frame.setSize(300, 250);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
