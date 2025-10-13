package org.example;

import static spark.Spark.*;
import org.example.transport.SistemaTransporte;

public class Servidor {
    public static void main(String[] args) {
        port(8080);
        SistemaTransporte sistema = new SistemaTransporte();

        System.out.println("Servidor iniciado em http://localhost:8080");
        System.out.println("Banco: transport.db\n");

        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type");
        });

        get("/motoristas", (req, res) -> {
            res.header("Content-Type", "application/json");
            return sistema.listarMotoristas().toString();
        });

        post("/motoristas", (req, res) -> {
            sistema.addMotorista(req.queryParams("nome"));
            return "Motorista adicionado!";
        });

        get("/veiculos", (req, res) -> {
            res.header("Content-Type", "application/json");
            return sistema.listarVeiculos().toString();
        });

        post("/veiculos", (req, res) -> {
            sistema.addVeiculo(req.queryParams("modelo"));
            return "Veículo adicionado!";
        });

        get("/rotas", (req, res) -> {
            res.header("Content-Type", "application/json");
            return sistema.listarRotas().toString();
        });

        post("/rotas", (req, res) -> {
            sistema.addRota(req.queryParams("descricao"));
            return "Rota adicionada!";
        });

        get("/viagens", (req, res) -> {
            res.header("Content-Type", "application/json");
            return sistema.listarViagens().toString();
        });

        post("/viagens", (req, res) -> {
            String motorista = req.queryParams("motorista");
            String veiculo = req.queryParams("veiculo");
            String rota = req.queryParams("rota");
            sistema.addViagem(motorista, veiculo, rota);
            return "Viagem registrada!";
        });

        options("/*", (req, res) -> {
            return "OK";
        });
    }
}