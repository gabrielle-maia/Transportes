package org.example;

import static spark.Spark.*;
import org.example.transport.SistemaTransporte;
import com.google.gson.Gson;

public class Servidor {
    public static void main(String[] args) {
        port(8080);
        SistemaTransporte sistema = new SistemaTransporte();
        Gson gson = new Gson();

        System.out.println("Servidor iniciado em http://localhost:8080");
        System.out.println("Banco: transport.db\n");

        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,POST,DELETE,OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type");
        });

        get("/motoristas", (req, res) -> {
            res.type("application/json");
            return gson.toJson(sistema.listarMotoristas());
        });

        post("/motoristas", (req, res) -> {
            sistema.addMotorista(req.queryParams("nome"));
            res.type("application/json");
            return gson.toJson("Motorista adicionado!");
        });

        delete("/motoristas/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            sistema.removerMotorista(id);
            res.type("application/json");
            return gson.toJson("Motorista removido!");
        });

        get("/veiculos", (req, res) -> {
            res.type("application/json");
            return gson.toJson(sistema.listarVeiculos());
        });

        post("/veiculos", (req, res) -> {
            sistema.addVeiculo(req.queryParams("modelo"));
            res.type("application/json");
            return gson.toJson("Veículo adicionado!");
        });

        delete("/veiculos/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            sistema.removerVeiculo(id);
            res.type("application/json");
            return gson.toJson("Veículo removido!");
        });

        get("/rotas", (req, res) -> {
            res.type("application/json");
            return gson.toJson(sistema.listarRotas());
        });

        post("/rotas", (req, res) -> {
            sistema.addRota(req.queryParams("descricao"));
            res.type("application/json");
            return gson.toJson("Rota adicionada!");
        });

        delete("/rotas/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            sistema.removerRota(id);
            res.type("application/json");
            return gson.toJson("Rota removida!");
        });

        get("/viagens", (req, res) -> {
            res.type("application/json");
            return gson.toJson(sistema.listarViagens());
        });

        post("/viagens", (req, res) -> {
            String motorista = req.queryParams("motorista");
            String veiculo = req.queryParams("veiculo");
            String rota = req.queryParams("rota");
            sistema.addViagem(motorista, veiculo, rota);
            res.type("application/json");
            return gson.toJson("Viagem registrada!");
        });

        delete("/viagens/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            sistema.removerViagem(id);
            res.type("application/json");
            return gson.toJson("Viagem removida!");
        });

        options("/*", (req, res) -> {
            res.type("application/json");
            return gson.toJson("OK");
        });
    }
}
