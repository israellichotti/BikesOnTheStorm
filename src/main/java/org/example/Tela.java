package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;



public class Tela {
    public JFrame frame;
    public BicicletaDAO bicicletaDAO;

    public Tela(JFrame frame, BicicletaDAO bicicletaDAO) {
        this.frame = frame;
        this.bicicletaDAO = bicicletaDAO;
    }

    public void mostrarMainMenu() {
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

    private void mostrarAdicionarBicicleta() {
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

                ResultSet rs = bicicletaDAO.listarBicicletas();
                int count = 1;
                if (rs != null) {
                    try {
                        rs.last();
                        count = rs.getRow() + 1;
                    } catch (SQLException ex) {
                        // Ignorar
                    }
                }
                String id = String.format("%03d", count);

                Bicicleta bicicleta = new Bicicleta(modelo, marca, tamanhoAro, base, aluguelDia, false);
                bicicletaDAO.adicionarBicicleta(bicicleta);

                JOptionPane.showMessageDialog(frame, "Bicicleta adicionada com sucesso!");
                mostrarMainMenu();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Tamanho do aro e aluguel base devem ser números.");
            }
        });

        btnVoltar.addActionListener(e -> mostrarMainMenu());

        frame.revalidate();
        frame.repaint();
    }

    private void mostrarListarBicicletas() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton btnVoltar = new JButton("Voltar");
        frame.add(btnVoltar, BorderLayout.SOUTH);

        ResultSet rs = bicicletaDAO.listarBicicletas();
        if (rs != null) {
            StringBuilder sb = new StringBuilder();
            try {
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
        }

        btnVoltar.addActionListener(e -> mostrarMainMenu());

        frame.revalidate();
        frame.repaint();
    }

    private void mostrarExcluirBicicleta() {
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

        ResultSet rs = bicicletaDAO.listarBicicletas();
        if (rs != null) {
            StringBuilder sb = new StringBuilder();
            try {
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
        }

        btnExcluir.addActionListener(e -> {
            String id = campoId.getText().trim();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Digite o ID da bicicleta que deseja excluir.");
                return;
            }

            bicicletaDAO.excluirBicicleta(id);
            JOptionPane.showMessageDialog(frame, "Bicicleta excluída com sucesso.");
            mostrarExcluirBicicleta();
        });

        btnVoltar.addActionListener(e -> mostrarMainMenu());

        frame.revalidate();
        frame.repaint();
    }

    private void mostrarBuscarBicicletaParaAluguel() {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(4, 1));

        JLabel lblId = new JLabel("Digite o ID da bicicleta que deseja alugar: ");
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

            Bicicleta bicicleta = bicicletaDAO.buscarBicicleta(id);
            if (bicicleta != null) {
                mostrarDadosBicicletaParaAluguel(bicicleta);
            } else {
                JOptionPane.showMessageDialog(frame, "Bicicleta não encontrada.");
            }
        });

        btnVoltar.addActionListener(e -> mostrarMainMenu());

        frame.revalidate();
        frame.repaint();
    }

    private void mostrarDadosBicicletaParaAluguel(Bicicleta bicicleta) {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(9, 1));

        frame.add(new JLabel("ID: " + bicicleta.getId()));
        frame.add(new JLabel("Modelo: " + bicicleta.getModelo()));
        frame.add(new JLabel("Marca: " + bicicleta.getMarca()));
        frame.add(new JLabel("Tamanho do Aro: " + bicicleta.getTamanhoAro()));
        frame.add(new JLabel("Aluguel Base: " + bicicleta.getAluguelBase()));
        frame.add(new JLabel("Aluguel Dia: " + bicicleta.getAluguelDia()));

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

                mostrarResumoParcialAluguel(bicicleta, dias);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Valor inválido. Digite um número inteiro valido.");
            }
        });

        btnVoltar.addActionListener(e -> mostrarBuscarBicicletaParaAluguel());

        frame.revalidate();
        frame.repaint();
    }

    private void mostrarResumoParcialAluguel(Bicicleta bicicleta, int dias) {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(10, 1));

        double aluguelTotal = bicicleta.getAluguelBase() + (bicicleta.getAluguelDia() * dias);

        frame.add(new JLabel("ID: " + bicicleta.getId()));
        frame.add(new JLabel("Modelo: " + bicicleta.getModelo()));
        frame.add(new JLabel("Marca: " + bicicleta.getMarca()));
        frame.add(new JLabel("Tamanho do Aro: " + bicicleta.getTamanhoAro()));
        frame.add(new JLabel("Aluguel Base: " + bicicleta.getAluguelBase()));
        frame.add(new JLabel("Aluguel Dia: " + bicicleta.getAluguelDia()));
        frame.add(new JLabel("Dias de aluguel: " + dias));
        frame.add(new JLabel("Valor total do aluguel: " + aluguelTotal));

        JButton btnProximo = new JButton("Próximo");
        JButton btnVoltar = new JButton("Voltar");
        frame.add(btnProximo);
        frame.add(btnVoltar);

        btnProximo.addActionListener(e -> mostrarFormularioCliente(bicicleta, dias, aluguelTotal));
        btnVoltar.addActionListener(e -> mostrarDadosBicicletaParaAluguel(bicicleta));

        frame.revalidate();
        frame.repaint();
    }

    private void mostrarFormularioCliente(Bicicleta bicicleta, int dias, double aluguelTotal) {
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

            mostrarResumoFinalAluguel(bicicleta, dias, aluguelTotal, nome, cpf, telefone);
        });

        btnVoltar.addActionListener(e -> mostrarResumoParcialAluguel(bicicleta, dias));

        frame.revalidate();
        frame.repaint();
    }

    private void mostrarResumoFinalAluguel(Bicicleta bicicleta, int dias, double aluguelTotal, String nome, String cpf, String telefone) {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(14, 1));

        frame.add(new JLabel("Resumo do Aluguel"));

        frame.add(new JLabel("ID: " + bicicleta.getId()));
        frame.add(new JLabel("Modelo: " + bicicleta.getModelo()));
        frame.add(new JLabel("Marca: " + bicicleta.getMarca()));
        frame.add(new JLabel("Tamanho do Aro: " + bicicleta.getTamanhoAro()));
        frame.add(new JLabel("Aluguel Base: " + bicicleta.getAluguelBase()));
        frame.add(new JLabel("Aluguel Dia: " + bicicleta.getAluguelDia()));
        frame.add(new JLabel("Dias de Aluguel: " + dias));
        frame.add(new JLabel("Total: " + aluguelTotal));

        frame.add(new JLabel("Nome do Cliente: " + nome));
        frame.add(new JLabel("CPF do Cliente: " + cpf));
        frame.add(new JLabel("Telefone do Cliente: " + telefone));

        JButton btnImprimir = new JButton("Imprimir");
        JButton btnVoltarMenu = new JButton("Voltar Menu");

        btnImprimir.addActionListener(e -> {
            bicicletaDAO.atualizarStatusAluguel(bicicleta.getId(), true);
            JOptionPane.showMessageDialog(frame, "Simulando a impressão :D");
            mostrarMainMenu();
        });

        btnVoltarMenu.addActionListener(e -> mostrarMainMenu());

        frame.add(btnImprimir);
        frame.add(btnVoltarMenu);

        frame.revalidate();
        frame.repaint();
    }
}