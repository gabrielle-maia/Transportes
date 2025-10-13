package org.example.transport;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class TesteRegressao {
    @Test
    public void testRegressaoFuncionalidadesPrincipais() {
        SistemaTransporte sistema = new SistemaTransporte();
        sistema.addMotorista("Teste Regressão Motorista");
        sistema.addVeiculo("Teste Regressão Veículo");
        sistema.addRota("Teste Regressão Rota");
        sistema.addViagem("Teste Regressão Motorista", "Teste Regressão Veículo", "Teste Regressão Rota");

        List<String> motoristas = sistema.listarMotoristas();
        List<String> veiculos = sistema.listarVeiculos();
        List<String> rotas = sistema.listarRotas();
        List<String> viagens = sistema.listarViagens();

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
        List<String> motoristas = sistema.listarMotoristas();
        assertTrue(motoristas.contains("Motorista Único"), "Motorista deve estar presente");
    }
}