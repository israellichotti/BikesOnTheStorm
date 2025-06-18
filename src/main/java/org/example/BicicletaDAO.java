package org.example;


import javax.swing.*;
import java.sql.*;


public class BicicletaDAO  {
    public Connection conn;
    public JFrame frame;

    public BicicletaDAO(Connection conn, JFrame frame) {
        this.conn = conn;
        this.frame = frame;
    }

    public void criarTabelaSeNaoExiste() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS bicicletas ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
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

    public void adicionarBicicleta(Bicicleta bicicleta) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO bicicletas (modelo, marca, tamanhoAro, aluguelBase, aluguelDia, alugada) VALUES (?, ?, ?, ?, ?, ?)");
            //pstmt.setInt(1, bicicleta.getId());
            pstmt.setString(1, bicicleta.getModelo());
            pstmt.setString(2, bicicleta.getMarca());
            pstmt.setDouble(3, bicicleta.getTamanhoAro());
            pstmt.setDouble(4, bicicleta.getAluguelBase());
            pstmt.setDouble(5, bicicleta.getAluguelDia());
            pstmt.setBoolean(6, bicicleta.isAlugada());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao salvar bicicleta: " + e.getMessage());
        }
    }

    public ResultSet listarBicicletas() {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery("SELECT * FROM bicicletas");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao listar bicicletas: " + e.getMessage());
            return null;
        }
    }

    public void excluirBicicleta(String id) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM bicicletas WHERE id = ?");
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao excluir bicicleta: " + e.getMessage());
        }
    }

    public Bicicleta buscarBicicleta(String id) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM bicicletas WHERE id = ?");
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Bicicleta(
                        rs.getInt("id"),
                        rs.getString("modelo"),
                        rs.getString("marca"),
                        rs.getDouble("tamanhoAro"),
                        rs.getDouble("aluguelBase"),
                        rs.getDouble("aluguelDia"),
                        rs.getBoolean("alugada")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao buscar bicicleta: " + e.getMessage());
            return null;
        }
    }

    public void atualizarStatusAluguel(int id, boolean alugada) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE bicicletas SET alugada = ? WHERE id = ?");
            pstmt.setBoolean(1, alugada);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao atualizar status de aluguel: " + e.getMessage());
        }
    }
}