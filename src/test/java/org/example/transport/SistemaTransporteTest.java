package org.example.transport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class SistemaTransporteTest {

    private SistemaTransporte sistema;

    @BeforeEach
    public void setUp() {
        sistema = new SistemaTransporte();
    }

    @Test
    public void testAdicionarMotorista() {
        String nomeMotorista = "João Silva Teste";

        sistema.addMotorista(nomeMotorista);
        List<String> motoristas = sistema.listarMotoristas();

        assertFalse(motoristas.isEmpty(), "A lista de motoristas não deve estar vazia");
        assertTrue(motoristas.contains(nomeMotorista), "A lista deve conter o motorista adicionado");
    }

    @Test
    public void testAdicionarVeiculo() {
        String modeloVeiculo = "Fiat Uno Teste";

        sistema.addVeiculo(modeloVeiculo);
        List<String> veiculos = sistema.listarVeiculos();

        assertFalse(veiculos.isEmpty(), "A lista de veículos não deve estar vazia");
        assertTrue(veiculos.contains(modeloVeiculo), "A lista deve conter o veículo adicionado");
    }

    @Test
    public void testAdicionarRota() {
        String descricaoRota = "São Paulo - Rio de Janeiro Teste";

        sistema.addRota(descricaoRota);
        List<String> rotas = sistema.listarRotas();

        assertFalse(rotas.isEmpty(), "A lista de rotas não deve estar vazia");
        assertTrue(rotas.contains(descricaoRota), "A lista deve conter a rota adicionada");
    }

    @Test
    public void testAdicionarViagem() {
        String motorista = "Maria Santos Teste";
        String veiculo = "Volkswagen Gol Teste";
        String rota = "Centro - Praia Teste";

        sistema.addMotorista(motorista);
        sistema.addVeiculo(veiculo);
        sistema.addRota(rota);
        sistema.addViagem(motorista, veiculo, rota);
        List<String> viagens = sistema.listarViagens();

        assertFalse(viagens.isEmpty(), "A lista de viagens não deve estar vazia");
        assertTrue(viagens.toString().contains(motorista), "A viagem deve conter o motorista");
    }

    @Test
    public void testFuncionalidadeBasica() {
        List<String> motoristas = sistema.listarMotoristas();
        List<String> veiculos = sistema.listarVeiculos();
        List<String> rotas = sistema.listarRotas();
        List<String> viagens = sistema.listarViagens();

        assertNotNull(motoristas, "Lista de motoristas não deve ser nula");
        assertNotNull(veiculos, "Lista de veículos não deve ser nula");
        assertNotNull(rotas, "Lista de rotas não deve ser nula");
        assertNotNull(viagens, "Lista de viagens não deve ser nula");
    }
}