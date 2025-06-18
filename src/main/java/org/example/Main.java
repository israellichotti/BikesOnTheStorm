import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class Main {
    private static final String DB_URL = "jdbc:sqlite:bicicletas.db";
    private static final String GERENTE_SENHA = "123456";
    private static Connection conn;
    private static JFrame frame = new JFrame();



    public static void main(String[] args) {
        connectDatabase();
        createTableIfNeeded();
        SwingUtilities.invokeLater(() -> {
            mostrarMainMenu();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void connectDatabase() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao conectar ao banco de dados: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void createTableIfNeeded() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS bicicletas ("
                    + "id TEXT PRIMARY KEY,"
                    + "modelo TEXT,"
                    + "marca TEXT,"
                    + "tamanhoAro REAL,"
                    + "aluguelBase REAL,"
                    + "aluguelDia REAL,"
                    + "alugada INTEGER"
                    + ")");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao criar tabela: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void mostrarAdicionarBicicleta() {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(8, 2));

        JLabel labelModelo = new JLabel("Modelo:");
        JTextField campoModelo = new JTextField();

        JLabel labelMarca = new JLabel("Marca:");
        JTextField campoMarca = new JTextField();

        JLabel labelAro = new JLabel("Tamanho do Aro:");
        JTextField campoAro = new JTextField();

        JLabel labelAluguelBase = new JLabel("Aluguel Base:");
        JTextField campoAluguelBase = new JTextField();

        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton(" Voltar");

        frame.add(labelModelo); frame.add(campoModelo);
        frame.add(labelMarca); frame.add(campoMarca);
        frame.add(labelAro); frame.add(campoAro);
        frame.add(labelAluguelBase); frame.add(campoAluguelBase);
        frame.add(btnSalvar); frame.add(btnVoltar);

        btnSalvar.addActionListener(e -> {
            String modelo = campoModelo.getText();
            String marca = campoMarca.getText();
            String aro = campoAro.getText();
            String aluguelBase = campoAluguelBase.getText();

            if (modelo.isEmpty() || marca.isEmpty() || aro.isEmpty() || aluguelBase.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Todos os campos devem ser preenchidos.");
                return;
            }

            try {
                double tamanhoAro = Double.parseDouble(aro);
                double base = Double.parseDouble(aluguelBase);
                double aluguelDia = 10.0;


                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM bicicletas");
                int count = rs.next() ? rs.getInt(1) + 1 : 1;
                String id = String.format("%03d", count);

                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO bicicletas (id, modelo, marca, tamanhoAro, aluguelBase, aluguelDia, alugada) VALUES (?, ?, ?, ?, ?, ?, ?)");
                pstmt.setString(1, id);
                pstmt.setString(2, modelo);
                pstmt.setString(3, marca);
                pstmt.setDouble(4, tamanhoAro);
                pstmt.setDouble(5, base);
                pstmt.setDouble(6, aluguelDia);
                pstmt.setBoolean(7, false);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Bicicleta adicionada com sucesso!");
                mostrarMainMenu();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Tamanho do aro e aluguel base devem ser números.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao salvar bicicleta: " + ex.getMessage());
            }
        });

        btnVoltar.addActionListener(e -> mostrarMainMenu());

        frame.revalidate();
        frame.repaint();
    }

    private static void mostrarMainMenu() {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(5, 1));

        JButton btnAdicionarBicicleta = new JButton("Adicionar Bicicleta");
        JButton btnListarBicicletas = new JButton("Listar Bicicletas");
        JButton btnExcluirBicicleta = new JButton("Excluir Bicicleta");
        JButton btnAlugarBicicleta = new JButton("Alugar Bicicleta");
        JButton btnSair = new JButton("Sair");

        frame.add(btnAdicionarBicicleta);
        frame.add(btnListarBicicletas);
        frame.add(btnExcluirBicicleta);
        frame.add(btnAlugarBicicleta);
        frame.add(btnSair);

        btnAdicionarBicicleta.addActionListener(e -> mostrarAdicionarBicicleta());
        btnListarBicicletas.addActionListener(e -> mostrarListarBicicletas());
        btnExcluirBicicleta.addActionListener(e -> mostrarExcluirBicicleta());
        btnAlugarBicicleta.addActionListener(e -> mostrarBuscarBicicletaParaAluguel());
        btnSair.addActionListener(e -> System.exit(0));

        frame.revalidate();
        frame.repaint();
    }




    private static void mostrarListarBicicletas() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton btnVoltar = new JButton("Voltar");
        frame.add(btnVoltar, BorderLayout.SOUTH);

        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM bicicletas");

            while (rs.next()) {
                sb.append("ID: ").append(rs.getString("id")).append("\n");
                sb.append("Modelo: ").append(rs.getString("modelo")).append("\n");
                sb.append("Marca: ").append(rs.getString("marca")).append("\n");
                sb.append("Tamanho do Aro: ").append(rs.getDouble("tamanhoAro")).append("\n");
                sb.append("Aluguel Base: ").append(rs.getDouble("aluguelBase")).append("\n");
                sb.append("Aluguel Dia: ").append(rs.getDouble("aluguelDia")).append("\n");
                sb.append("Alugada: ").append(rs.getBoolean("alugada") ? "Sim" : "Não").append("\n");
                sb.append("---------------------------\n");
            }

            textArea.setText(sb.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao listar bicicletas: " + e.getMessage());
        }

        btnVoltar.addActionListener(e -> mostrarMainMenu());

        frame.revalidate();
        frame.repaint();
    }




    private static void mostrarExcluirBicicleta() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 2));
        JLabel labelId = new JLabel("ID da bicicleta para excluir:");
        JTextField campoId = new JTextField();
        JButton btnExcluir = new JButton("Excluir");
        JButton btnVoltar = new JButton("Voltar");

        bottomPanel.add(labelId);
        bottomPanel.add(campoId);
        bottomPanel.add(btnExcluir);
        bottomPanel.add(btnVoltar);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Listar as bicicletas
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM bicicletas");

            while (rs.next()) {
                sb.append("ID: ").append(rs.getString("id")).append("\n");
                sb.append("Modelo: ").append(rs.getString("modelo")).append("\n");
                sb.append("Marca: ").append(rs.getString("marca")).append("\n");
                sb.append("Tamanho do Aro: ").append(rs.getDouble("tamanhoAro")).append("\n");
                sb.append("Aluguel Base: ").append(rs.getDouble("aluguelBase")).append("\n");
                sb.append("Aluguel Dia: ").append(rs.getDouble("aluguelDia")).append("\n");
                sb.append("Alugada: ").append(rs.getBoolean("alugada") ? "Sim" : "Não").append("\n");
                sb.append("---------------------------\n");
            }

            textArea.setText(sb.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao carregar bicicletas: " + e.getMessage());
        }

        btnExcluir.addActionListener(e -> {
            String id = campoId.getText().trim();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Digite o ID da bicicleta que deseja excluir.");
                return;
            }

            try {
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM bicicletas WHERE id = ?");
                pstmt.setString(1, id);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, "Bicicleta excluída com sucesso.");
                    mostrarExcluirBicicleta(); // Atualiza a lista
                } else {
                    JOptionPane.showMessageDialog(frame, "Bicicleta com ID " + id + " não encontrada.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao excluir bicicleta: " + ex.getMessage());
            }
        });

        btnVoltar.addActionListener(e -> mostrarMainMenu());

        frame.revalidate();
        frame.repaint();
    }




    private static void mostrarBuscarBicicletaParaAluguel() {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(4, 1));

        JLabel lblId = new JLabel("Digite o ID da bicicleta que deseja alugar (entre 001 a 999):");
        JTextField campoId = new JTextField();
        JButton btnProximo = new JButton("Proximo");
        JButton btnVoltar = new JButton("Voltar");

        frame.add(lblId);
        frame.add(campoId);
        frame.add(btnProximo);
        frame.add(btnVoltar);

        btnProximo.addActionListener(e -> {
            String id = campoId.getText().trim();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Campo de ID vazio.");
                return;
            }

            try {
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM bicicletas WHERE id = ?");
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String modelo = rs.getString("modelo");
                    String marca = rs.getString("marca");
                    double tamanhoAro = rs.getDouble("tamanhoAro");
                    double aluguelBase = rs.getDouble("aluguelBase");
                    double aluguelDia = rs.getDouble("aluguelDia");

                    mostrarDadosBicicletaParaAluguel(id, modelo, marca, tamanhoAro, aluguelBase, aluguelDia);
                } else {
                    JOptionPane.showMessageDialog(frame, "Bicicleta não encontrada.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao buscar bicicleta: " + ex.getMessage());
            }
        });

        btnVoltar.addActionListener(e -> mostrarMainMenu());

        frame.revalidate();
        frame.repaint();
    }




    private static void mostrarDadosBicicletaParaAluguel(String id, String modelo, String marca, double tamanhoAro, double aluguelBase, double aluguelDia) {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(9, 1));

        frame.add(new JLabel("ID: " + id));
        frame.add(new JLabel("Modelo: " + modelo));
        frame.add(new JLabel("Marca: " + marca));
        frame.add(new JLabel("Tamanho do Aro: " + tamanhoAro));
        frame.add(new JLabel("Aluguel Base: " + aluguelBase));
        frame.add(new JLabel("Aluguel Dia: " + aluguelDia));

        JLabel lblDias = new JLabel("Quantos dias deseja alugar? (1 a 28)");
        JTextField campoDias = new JTextField();
        JButton btnProximo = new JButton("Próximo");
        JButton btnVoltar = new JButton("Voltar");

        frame.add(lblDias);
        frame.add(campoDias);
        frame.add(btnProximo);
        frame.add(btnVoltar);

        btnProximo.addActionListener(e -> {
            String textoDias = campoDias.getText().trim();

            try {
                int dias = Integer.parseInt(textoDias);
                if (dias < 1 || dias > 28) {
                    JOptionPane.showMessageDialog(frame, "Valor invalido. So pode digitar um número entre 1 e 28.");
                    return;
                }

                mostrarResumoParcialAluguel(id, modelo, marca, tamanhoAro, aluguelBase, aluguelDia, dias);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Valor inválido. Digite um número inteiro valido.");
            }
        });

        btnVoltar.addActionListener(e -> mostrarBuscarBicicletaParaAluguel());

        frame.revalidate();
        frame.repaint();
    }




    private static void mostrarResumoParcialAluguel(String id, String modelo, String marca, double tamanhoAro, double aluguelBase, double aluguelDia, int dias) {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(10, 1));

        double aluguelTotal = aluguelBase + (aluguelDia * dias);

        frame.add(new JLabel("ID: " + id));
        frame.add(new JLabel("Modelo: " + modelo));
        frame.add(new JLabel("Marca: " + marca));
        frame.add(new JLabel("Tamanho do Aro: " + tamanhoAro));
        frame.add(new JLabel("Aluguel Base: " + aluguelBase));
        frame.add(new JLabel("Aluguel Dia: " + aluguelDia));
        frame.add(new JLabel("Dias de aluguel: " + dias));
        frame.add(new JLabel("Valor total do aluguel: " + aluguelTotal));

        JButton btnProximo = new JButton("Próximo");
        JButton btnVoltar = new JButton("Voltar");
        frame.add(btnProximo);
        frame.add(btnVoltar);

        btnProximo.addActionListener(e -> mostrarFormularioCliente(id, modelo, marca, tamanhoAro, aluguelBase, aluguelDia, dias, aluguelTotal));
        btnVoltar.addActionListener(e -> mostrarDadosBicicletaParaAluguel(id, modelo, marca, tamanhoAro, aluguelBase, aluguelDia));

        frame.revalidate();
        frame.repaint();
    }




    private static void mostrarFormularioCliente(String id, String modelo, String marca, double tamanhoAro, double aluguelBase, double aluguelDia, int dias, double aluguelTotal) {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(7, 2));

        JTextField campoNome = new JTextField();
        JTextField campoCpf = new JTextField();
        JTextField campoTelefone = new JTextField();

        frame.add(new JLabel("Nome:"));
        frame.add(campoNome);
        frame.add(new JLabel("CPF:"));
        frame.add(campoCpf);
        frame.add(new JLabel("Telefone:"));
        frame.add(campoTelefone);

        JButton btnProximo = new JButton("Próximo");
        JButton btnVoltar = new JButton("Voltar");

        frame.add(btnProximo);
        frame.add(btnVoltar);

        btnProximo.addActionListener(e -> {
            String nome = campoNome.getText().trim();
            String cpf = campoCpf.getText().trim();
            String telefone = campoTelefone.getText().trim();

            if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Formulário não preenchido.");
                return;
            }

            mostrarResumoFinalAluguel(id, modelo, marca, tamanhoAro, aluguelBase, aluguelDia, dias, aluguelTotal, nome, cpf, telefone);
        });

        btnVoltar.addActionListener(e -> mostrarResumoParcialAluguel(id, modelo, marca, tamanhoAro, aluguelBase, aluguelDia, dias));

        frame.revalidate();
        frame.repaint();
    }




    private static void mostrarResumoFinalAluguel(String id, String modelo, String marca, double tamanhoAro, double aluguelBase, double aluguelDia, int dias, double aluguelTotal, String nome, String cpf, String telefone) {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(14, 1));

        frame.add(new JLabel("Resumo do Aluguel"));

        frame.add(new JLabel("---- Bicicleta ----"));
        frame.add(new JLabel("ID: " + id));
        frame.add(new JLabel("Modelo: " + modelo));
        frame.add(new JLabel("Marca: " + marca));
        frame.add(new JLabel("Tamanho do Aro: " + tamanhoAro));
        frame.add(new JLabel("Aluguel Base: " + aluguelBase));
        frame.add(new JLabel("Aluguel Dia: " + aluguelDia));
        frame.add(new JLabel("Dias de Aluguel: " + dias));
        frame.add(new JLabel("Total: " + aluguelTotal));

        frame.add(new JLabel("---- Cliente ----"));
        frame.add(new JLabel("Nome: " + nome));
        frame.add(new JLabel("CPF: " + cpf));
        frame.add(new JLabel("Telefone: " + telefone));

        JButton btnImprimir = new JButton("Imprimir");
        JButton btnVoltarMenu = new JButton("Voltar Menu");

        btnImprimir.addActionListener(e -> {
            try {
                PreparedStatement pstmt = conn.prepareStatement("UPDATE bicicletas SET alugada = 1 WHERE id = ?");
                pstmt.setString(1, id);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Simulando a impressão :D");
                mostrarMainMenu();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao atualizar status de aluguel: " + ex.getMessage());
            }
        });

        btnVoltarMenu.addActionListener(e -> mostrarMainMenu());

        frame.add(btnImprimir);
        frame.add(btnVoltarMenu);

        frame.revalidate();
        frame.repaint();
    }

}