package org.example.transport;

import java.sql.*;
import java.util.*;

public class SistemaTransporte {
    private Connection conn;

    public SistemaTransporte() {
        conectar();
        criarTabelas();
    }

    private void conectar() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:transport.db");
            System.out.println("Conectado ao banco de dados SQLite.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco: " + e.getMessage());
        }
    }

    private void criarTabelas() {
        try (Statement st = conn.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS motoristas (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT)");
            st.execute("CREATE TABLE IF NOT EXISTS veiculos (id INTEGER PRIMARY KEY AUTOINCREMENT, modelo TEXT)");
            st.execute("CREATE TABLE IF NOT EXISTS rotas (id INTEGER PRIMARY KEY AUTOINCREMENT, descricao TEXT)");
            st.execute("CREATE TABLE IF NOT EXISTS viagens (id INTEGER PRIMARY KEY AUTOINCREMENT, motorista TEXT, veiculo TEXT, rota TEXT)");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }

    public void addMotorista(String nome) {
        executarInsert("INSERT INTO motoristas(nome) VALUES(?)", nome);
    }

    public List<String> listarMotoristas() {
        return listar("SELECT nome FROM motoristas");
    }

    public void addVeiculo(String modelo) {
        executarInsert("INSERT INTO veiculos(modelo) VALUES(?)", modelo);
    }

    public List<String> listarVeiculos() {
        return listar("SELECT modelo FROM veiculos");
    }

    public void addRota(String descricao) {
        executarInsert("INSERT INTO rotas(descricao) VALUES(?)", descricao);
    }

    public List<String> listarRotas() {
        return listar("SELECT descricao FROM rotas");
    }

    public void addViagem(String motorista, String veiculo, String rota) {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO viagens(motorista, veiculo, rota) VALUES(?,?,?)")) {
            ps.setString(1, motorista);
            ps.setString(2, veiculo);
            ps.setString(3, rota);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao registrar viagem: " + e.getMessage());
        }
    }

    public List<String> listarViagens() {
        List<String> lista = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT motorista, veiculo, rota FROM viagens")) {
            while (rs.next()) {
                lista.add(rs.getString("motorista") + " - " +
                        rs.getString("veiculo") + " - " +
                        rs.getString("rota"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar viagens: " + e.getMessage());
        }
        return lista;
    }

    private void executarInsert(String sql, String valor) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, valor);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir: " + e.getMessage());
        }
    }

    private List<String> listar(String sql) {
        List<String> lista = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(rs.getString(1));
        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }
        return lista;
    }
}


