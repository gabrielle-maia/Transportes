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

    public List<Map<String, Object>> listarMotoristas() {
        return listarComId("SELECT id, nome FROM motoristas");
    }

    public void removerMotorista(int id) {
        executarDelete("DELETE FROM motoristas WHERE id = ?", id);
        resetarAutoIncrementoSeVazio("motoristas");
    }

    public void addVeiculo(String modelo) {
        executarInsert("INSERT INTO veiculos(modelo) VALUES(?)", modelo);
    }

    public List<Map<String, Object>> listarVeiculos() {
        return listarComId("SELECT id, modelo FROM veiculos");
    }

    public void removerVeiculo(int id) {
        executarDelete("DELETE FROM veiculos WHERE id = ?", id);
        resetarAutoIncrementoSeVazio("veiculos");
    }

    public void addRota(String descricao) {
        executarInsert("INSERT INTO rotas(descricao) VALUES(?)", descricao);
    }

    public List<Map<String, Object>> listarRotas() {
        return listarComId("SELECT id, descricao FROM rotas");
    }

    public void removerRota(int id) {
        executarDelete("DELETE FROM rotas WHERE id = ?", id);
        resetarAutoIncrementoSeVazio("rotas");
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

    public List<Map<String, Object>> listarViagens() {
        List<Map<String, Object>> lista = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, motorista, veiculo, rota FROM viagens")) {
            while (rs.next()) {
                Map<String, Object> viagem = new HashMap<>();
                viagem.put("id", rs.getInt("id"));
                viagem.put("motorista", rs.getString("motorista"));
                viagem.put("veiculo", rs.getString("veiculo"));
                viagem.put("rota", rs.getString("rota"));
                lista.add(viagem);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar viagens: " + e.getMessage());
        }
        return lista;
    }

    public void removerViagem(int id) {
        executarDelete("DELETE FROM viagens WHERE id = ?", id);
        resetarAutoIncrementoSeVazio("viagens");
    }

    private void executarInsert(String sql, String valor) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, valor);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir: " + e.getMessage());
        }
    }

    private void executarDelete(String sql, int id) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        }
    }

    private List<Map<String, Object>> listarComId(String sql) {
        List<Map<String, Object>> lista = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            ResultSetMetaData meta = rs.getMetaData();
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    item.put(meta.getColumnName(i), rs.getObject(i));
                }
                lista.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }
        return lista;
    }

    private void resetarAutoIncrementoSeVazio(String tabela) {
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT COUNT(*) AS total FROM " + tabela);
            if (rs.next() && rs.getInt("total") == 0) {
                st.execute("DELETE FROM sqlite_sequence WHERE name='" + tabela + "'");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao resetar autoincremento: " + e.getMessage());
        }
    }
}
