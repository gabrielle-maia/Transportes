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
        sistema = new SistemaTransporte();
    }

    @Test
    public void testFluxoCompletoSistema() {
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

        assertAll("Verificação do fluxo completo",
                () -> assertTrue(motoristas.contains(motorista), "Motorista deve estar na lista"),
                () -> assertTrue(veiculos.contains(veiculo), "Veículo deve estar na lista"),
                () -> assertTrue(rotas.contains(rota), "Rota deve estar na lista"),
                () -> assertTrue(viagens.toString().contains(motorista), "Viagem deve referenciar o motorista")
        );
    }

    @Test
    public void testMultiplosRegistros() {
        long timestamp = System.currentTimeMillis();

        sistema.addMotorista("Motorista Teste A " + timestamp);
        sistema.addMotorista("Motorista Teste B " + timestamp);
        sistema.addVeiculo("Veículo Teste A " + timestamp);
        sistema.addVeiculo("Veículo Teste B " + timestamp);

        List<Map<String, Object>> motoristas = sistema.listarMotoristas();
        List<Map<String, Object>> veiculos = sistema.listarVeiculos();

        assertTrue(motoristas.size() >= 2, "Deve ter pelo menos 2 motoristas");
        assertTrue(veiculos.size() >= 2, "Deve ter pelo menos 2 veículos");
    }
}