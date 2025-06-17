package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JPanel mainPanel;

    public static void main(String[] args) {
        frame = new JFrame("Bikes on the Storm");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Usando CardLayout para gerenciar diferentes telas
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Tela do Menu Principal
        JPanel menuPanel = new JPanel();
        JButton btnGerenciar = new JButton("Gerenciar Bicicletas");
        JButton btnAlugar = new JButton("Alugar Bicicletas");
        JButton btnListar = new JButton("Listar Bicicletas");

        menuPanel.add(btnGerenciar);
        menuPanel.add(btnAlugar);
        menuPanel.add(btnListar);

        // Ação do botão Gerenciar
        btnGerenciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "gerenciarPanel");
            }
        });

        // Ação do botão Alugar
        btnAlugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "alugarPanel");
            }
        });

        // Ação do botão Listar
        btnListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "listarPanel");
            }
        });

        // Adicionando o painel do menu ao mainPanel
        mainPanel.add(menuPanel, "menuPanel");

        // Tela de Gerenciamento
        JPanel gerenciarPanel = new JPanel();
        JButton btnVoltarGerenciar = new JButton("Voltar ao Menu");
        gerenciarPanel.add(new JLabel("Gerenciar Bicicletas"));
        gerenciarPanel.add(btnVoltarGerenciar);

        // Ação do botão Voltar
        btnVoltarGerenciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "menuPanel");
            }
        });

        // Adicionando o painel de gerenciamento ao mainPanel
        mainPanel.add(gerenciarPanel, "gerenciarPanel");

        // Tela de Aluguel
        JPanel alugarPanel = new JPanel();
        JButton btnVoltarAlugar = new JButton("Voltar ao Menu");
        alugarPanel.add(new JLabel("Alugar Bicicletas"));
        alugarPanel.add(btnVoltarAlugar);

        // Ação do botão Voltar
        btnVoltarAlugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "menuPanel");
            }
        });

        // Adicionando o painel de aluguel ao mainPanel
        mainPanel.add(alugarPanel, "alugarPanel");

        // Tela de Listagem
        JPanel listarPanel = new JPanel();
        JButton btnVoltarListar = new JButton("Voltar ao Menu");
        listarPanel.add(new JLabel("Listar Bicicletas"));
        listarPanel.add(btnVoltarListar);

        // Ação do botão Voltar
        btnVoltarListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "menuPanel");
            }
        });

        // Adicionando o painel de listagem ao mainPanel
        mainPanel.add(listarPanel, "listarPanel");

        // Adicionando o mainPanel ao frame
        frame.add(mainPanel);
        frame.setVisible(true);



    }

    private static void imprimirRecibo() {
    }

    private static void mostrarBicicleta() {
    }

    private static void procurarID() {
    }

    private static void excluiBicicleta() {
    }

    private static void adicionaBicicleta() {
    }

    private static void confereSenha() {

    }
}