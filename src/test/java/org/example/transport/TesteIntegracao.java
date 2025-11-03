package org.example.transport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Map;

public class TesteIntegracao {

    private SistemaTransporte sistema;

    @BeforeEach
    public void setUp() {
        SistemaTransporte.limparBancoTeste();
        sistema = new SistemaTransporte("transport_test.db");
        System.out.println("\n [SETUP] Banco de testes limpo e inicializado para Teste de Integração.");
    }

    @Test
    public void testFluxoCompletoSistema() {
        System.out.println("\n [TESTE] testFluxoCompletoSistema iniciado...");

        String motorista = "Carlos Oliveira Teste " + System.currentTimeMillis();
        String veiculo = "Toyota Corolla Teste " + System.currentTimeMillis();
        String rota = "Aeroporto - Centro Teste " + System.currentTimeMillis();

        sistema.addMotorista(motorista);
        sistema.addVeiculo(veiculo);
        sistema.addRota(rota);
        sistema.addViagem(motorista, veiculo, rota);

        List<Map<String, Object>> motoristas = sistema.listarMotoristas();
        List<Map<String, Object>> veiculos = sistema.listarVeiculos();
        List<Map<String, Object>> rotas = sistema.listarRotas();
        List<Map<String, Object>> viagens = sistema.listarViagens();

        System.out.println(" Estado atual após execução:");
        System.out.println("Motoristas: " + motoristas);
        System.out.println("Veículos: " + veiculos);
        System.out.println("Rotas: " + rotas);
        System.out.println("Viagens: " + viagens);

        assertAll("Verificação do fluxo completo",
                () -> assertTrue(
                        motoristas.stream().anyMatch(m -> motorista.equals(m.get("nome"))),
                        "Motorista deve estar na lista"
                ),
                () -> assertTrue(
                        veiculos.stream().anyMatch(v -> veiculo.equals(v.get("modelo"))),
                        "Veículo deve estar na lista"
                ),
                () -> assertTrue(
                        rotas.stream().anyMatch(r -> rota.equals(r.get("descricao"))),
                        "Rota deve estar na lista"
                ),
                () -> assertTrue(
                        viagens.stream().anyMatch(v -> motorista.equals(v.get("motorista"))),
                        "Viagem deve referenciar o motorista"
                )
        );

        System.out.println(" Teste 'testFluxoCompletoSistema' passou com sucesso!");
    }

    @Test
    public void testMultiplosRegistros() {
        System.out.println("\n [TESTE] testMultiplosRegistros iniciado...");

        long timestamp = System.currentTimeMillis();

        sistema.addMotorista("Motorista Teste A " + timestamp);
        sistema.addMotorista("Motorista Teste B " + timestamp);
        sistema.addVeiculo("Veículo Teste A " + timestamp);
        sistema.addVeiculo("Veículo Teste B " + timestamp);

        List<Map<String, Object>> motoristas = sistema.listarMotoristas();
        List<Map<String, Object>> veiculos = sistema.listarVeiculos();

        System.out.println("Motoristas cadastrados: " + motoristas.size());
        System.out.println(" Veículos cadastrados: " + veiculos.size());
        System.out.println("Detalhes:");
        System.out.println(" Motoristas: " + motoristas);
        System.out.println("Veículos: " + veiculos);

        assertTrue(
                motoristas.size() >= 2,
                "Deve ter pelo menos 2 motoristas"
        );
        assertTrue(
                veiculos.size() >= 2,
                "Deve ter pelo menos 2 veículos"
        );

        System.out.println(" Teste 'testMultiplosRegistros' passou com sucesso!");
    }
}
