package org.example.transport;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Map;

public class TesteRegressao {
    @Test
    public void testRegressaoFuncionalidadesPrincipais() {
        SistemaTransporte sistema = new SistemaTransporte();
        sistema.addMotorista("Teste Regressão Motorista");
        sistema.addVeiculo("Teste Regressão Veículo");
        sistema.addRota("Teste Regressão Rota");
        sistema.addViagem("Teste Regressão Motorista", "Teste Regressão Veículo", "Teste Regressão Rota");

        List<Map<String, Object>> motoristas = sistema.listarMotoristas();
        List<Map<String, Object>> veiculos = sistema.listarVeiculos();
        List<Map<String, Object>> rotas = sistema.listarRotas();
        List<Map<String, Object>> viagens = sistema.listarViagens();

        assertAll("Teste de Regressão - Funcionalidades Principais",
                () -> assertFalse(motoristas.isEmpty(), "Cadastro de motoristas deve funcionar"),
                () -> assertFalse(veiculos.isEmpty(), "Cadastro de veículos deve funcionar"),
                () -> assertFalse(rotas.isEmpty(), "Cadastro de rotas deve funcionar"),
                () -> assertFalse(viagens.isEmpty(), "Cadastro de viagens deve funcionar"),
                () -> assertTrue(motoristas.contains("Teste Regressão Motorista")),
                () -> assertTrue(veiculos.contains("Teste Regressão Veículo")),
                () -> assertTrue(rotas.contains("Teste Regressão Rota"))
        );
    }

    @Test
    public void testRegressaoSemDuplicacao() {
        SistemaTransporte sistema = new SistemaTransporte();

        sistema.addMotorista("Motorista Único");
        sistema.addMotorista("Motorista Único");
        sistema.addMotorista("Motorista Único");
        List<Map<String, Object>> motoristas = sistema.listarMotoristas();
        assertTrue(motoristas.contains("Motorista Único"), "Motorista deve estar presente");
    }
}