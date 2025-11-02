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
            res.header("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
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

        get("/veiculos", (req, res) -> {
            res.type("application/json");
            return gson.toJson(sistema.listarVeiculos());
        });

        post("/veiculos", (req, res) -> {
            sistema.addVeiculo(req.queryParams("modelo"));
            res.type("application/json");
            return gson.toJson("Veículo adicionado!");
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

        options("/*", (req, res) -> {
            res.type("application/json");
            return gson.toJson("OK");
        });
    }
}