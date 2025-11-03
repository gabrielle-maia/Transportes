package org.example.transport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Map;

public class SistemaTransporteTest {

    private SistemaTransporte sistema;

    @BeforeEach
    public void setUp() {
        SistemaTransporte.limparBancoTeste();
        sistema = new SistemaTransporte("transport_test.db");
        System.out.println("\n[SETUP] Banco de testes limpo e inicializado.");
    }

    @Test
    public void testAdicionarMotorista() {
        System.out.println("\n [TESTE] testAdicionarMotorista iniciado...");
        String nomeMotorista = "João Silva Teste";
        sistema.addMotorista(nomeMotorista);

        List<Map<String, Object>> motoristas = sistema.listarMotoristas();
        System.out.println(" Motoristas cadastrados: " + motoristas);

        assertFalse(motoristas.isEmpty(), "A lista de motoristas não deve estar vazia");
        assertTrue(
                motoristas.stream().anyMatch(m -> nomeMotorista.equals(m.get("nome"))),
                "A lista deve conter o motorista adicionado"
        );
        System.out.println("Teste 'testAdicionarMotorista' passou com sucesso!");
    }

    @Test
    public void testAdicionarVeiculo() {
        System.out.println("\n[TESTE] testAdicionarVeiculo iniciado...");
        String modeloVeiculo = "Fiat Uno Teste";
        sistema.addVeiculo(modeloVeiculo);

        List<Map<String, Object>> veiculos = sistema.listarVeiculos();
        System.out.println("Veículos cadastrados: " + veiculos);

        assertFalse(veiculos.isEmpty(), "A lista de veículos não deve estar vazia");
        assertTrue(
                veiculos.stream().anyMatch(v -> modeloVeiculo.equals(v.get("modelo"))),
                "A lista deve conter o veículo adicionado"
        );
        System.out.println("Teste 'testAdicionarVeiculo' passou com sucesso!");
    }

    @Test
    public void testAdicionarRota() {
        System.out.println("\n [TESTE] testAdicionarRota iniciado...");
        String descricaoRota = "São Paulo - Rio de Janeiro Teste";
        sistema.addRota(descricaoRota);

        List<Map<String, Object>> rotas = sistema.listarRotas();
        System.out.println("Rotas cadastradas: " + rotas);

        assertFalse(rotas.isEmpty(), "A lista de rotas não deve estar vazia");
        assertTrue(
                rotas.stream().anyMatch(r -> descricaoRota.equals(r.get("descricao"))),
                "A lista deve conter a rota adicionada"
        );
        System.out.println(" Teste 'testAdicionarRota' passou com sucesso!");
    }

    @Test
    public void testAdicionarViagem() {
        System.out.println("\n [TESTE] testAdicionarViagem iniciado...");
        String motorista = "Maria Santos Teste";
        String veiculo = "Volkswagen Gol Teste";
        String rota = "Centro - Praia Teste";

        sistema.addMotorista(motorista);
        sistema.addVeiculo(veiculo);
        sistema.addRota(rota);
        sistema.addViagem(motorista, veiculo, rota);

        List<Map<String, Object>> viagens = sistema.listarViagens();
        System.out.println(" Viagens registradas: " + viagens);

        assertFalse(viagens.isEmpty(), "A lista de viagens não deve estar vazia");
        assertTrue(
                viagens.stream().anyMatch(v -> motorista.equals(v.get("motorista"))),
                "A viagem deve conter o motorista"
        );
        System.out.println("Teste 'testAdicionarViagem' passou com sucesso!");
    }

    @Test
    public void testFuncionalidadeBasica() {
        System.out.println("\n [TESTE] testFuncionalidadeBasica iniciado...");
        List<Map<String, Object>> motoristas = sistema.listarMotoristas();
        List<Map<String, Object>> veiculos = sistema.listarVeiculos();
        List<Map<String, Object>> rotas = sistema.listarRotas();
        List<Map<String, Object>> viagens = sistema.listarViagens();

        System.out.println(" Estado atual do sistema:");
        System.out.println("Motoristas: " + motoristas.size());
        System.out.println("Veículos: " + veiculos.size());
        System.out.println("Rotas: " + rotas.size());
        System.out.println("Viagens: " + viagens.size());

        assertNotNull(motoristas, "Lista de motoristas não deve ser nula");
        assertNotNull(veiculos, "Lista de veículos não deve ser nula");
        assertNotNull(rotas, "Lista de rotas não deve ser nula");
        assertNotNull(viagens, "Lista de viagens não deve ser nula");
        System.out.println("Teste 'testFuncionalidadeBasica' passou com sucesso!");
    }
}
