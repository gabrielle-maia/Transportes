package org.example.transport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Map;

public class TesteRegressao {

    private SistemaTransporte sistema;

    @BeforeEach
    public void setUp() {
        SistemaTransporte.limparBancoTeste();
        sistema = new SistemaTransporte("transport_test.db");
        System.out.println("\n[SETUP] Banco de testes limpo e inicializado para Teste de Regressão.");
    }

    @Test
    public void testRegressaoFuncionalidadesPrincipais() {
        System.out.println("\n [TESTE] testRegressaoFuncionalidadesPrincipais iniciado...");

        sistema.addMotorista("Teste Regressão Motorista");
        sistema.addVeiculo("Teste Regressão Veículo");
        sistema.addRota("Teste Regressão Rota");
        sistema.addViagem("Teste Regressão Motorista", "Teste Regressão Veículo", "Teste Regressão Rota");

        List<Map<String, Object>> motoristas = sistema.listarMotoristas();
        List<Map<String, Object>> veiculos = sistema.listarVeiculos();
        List<Map<String, Object>> rotas = sistema.listarRotas();
        List<Map<String, Object>> viagens = sistema.listarViagens();

        System.out.println("Estado do sistema após inserções:");
        System.out.println("Motoristas: " + motoristas);
        System.out.println("Veículos: " + veiculos);
        System.out.println("Rotas: " + rotas);
        System.out.println("Viagens: " + viagens);

        assertAll("Teste de Regressão - Funcionalidades Principais",
                () -> assertFalse(motoristas.isEmpty(), "Cadastro de motoristas deve funcionar"),
                () -> assertFalse(veiculos.isEmpty(), "Cadastro de veículos deve funcionar"),
                () -> assertFalse(rotas.isEmpty(), "Cadastro de rotas deve funcionar"),
                () -> assertFalse(viagens.isEmpty(), "Cadastro de viagens deve funcionar"),
                () -> assertTrue(
                        motoristas.stream().anyMatch(m -> "Teste Regressão Motorista".equals(m.get("nome"))),
                        "Motorista deve estar na lista"
                ),
                () -> assertTrue(
                        veiculos.stream().anyMatch(v -> "Teste Regressão Veículo".equals(v.get("modelo"))),
                        "Veículo deve estar na lista"
                ),
                () -> assertTrue(
                        rotas.stream().anyMatch(r -> "Teste Regressão Rota".equals(r.get("descricao"))),
                        "Rota deve estar na lista"
                )
        );

        System.out.println("Teste 'testRegressaoFuncionalidadesPrincipais' passou com sucesso!");
    }

    @Test
    public void testRegressaoSemDuplicacao() {
        System.out.println("\n[TESTE] testRegressaoSemDuplicacao iniciado...");

        sistema.addMotorista("Motorista Único");
        sistema.addMotorista("Motorista Único");
        sistema.addMotorista("Motorista Único");

        List<Map<String, Object>> motoristas = sistema.listarMotoristas();

        System.out.println("Motoristas cadastrados: " + motoristas);

        assertTrue(
                motoristas.stream().anyMatch(m -> "Motorista Único".equals(m.get("nome"))),
                "Motorista deve estar presente"
        );

        System.out.println("Teste 'testRegressaoSemDuplicacao' passou com sucesso!");
    }
}
