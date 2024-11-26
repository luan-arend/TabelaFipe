package br.com.luarend.TabelaFipe.principal;

import br.com.luarend.TabelaFipe.model.DadosModelo;
import br.com.luarend.TabelaFipe.model.DadosVeiculo;
import br.com.luarend.TabelaFipe.model.Veiculo;
import br.com.luarend.TabelaFipe.service.HttpRequests;
import br.com.luarend.TabelaFipe.service.JsonDataMapper;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private HttpRequests httpRequests = new HttpRequests();
    private JsonDataMapper jsonDataMapper = new JsonDataMapper();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu(){
        System.out.println("Digite o que deseja buscar: " +
                "Carros / Caminhao / Moto");

        var input = scanner.nextLine();
        String endereco;

        if (input.toLowerCase().contains("carr")) {
            endereco = URL_BASE + "carros/marcas/";
        } else if (input.toLowerCase().contains("caminh")) {
            endereco = URL_BASE + "caminhoes/marcas/";
        } else if (input.toLowerCase().contains("moto")) {
            endereco = URL_BASE + "motos/marcas/";
        } else {
            System.out.println("Opção inválida, tente novamente!");
            return;
        }

        var json = httpRequests.sendRequest(endereco);
        var dadosVeiculos = jsonDataMapper.convertJsonToList(json, DadosVeiculo.class);

        dadosVeiculos.stream()
                .sorted(Comparator.comparing(DadosVeiculo::nome))
                .forEach(System.out::println);
        System.out.println("*** Informe o CÓDIGO da marca para consulta: ***");
        var codigoMarca = scanner.nextLine();

        endereco = endereco + codigoMarca + "/modelos/";
        json = httpRequests.sendRequest(endereco);
        var dadosModelos = jsonDataMapper.convertJsonToObject(json, DadosModelo.class);

        dadosModelos.modelos().stream()
                .sorted(Comparator.comparing(DadosVeiculo::nome))
                .forEach(System.out::println);

        System.out.println("\nDigite uma parte do nome do carro para ser buscado:");
        var nomeVeiculo = scanner.nextLine();

        List<DadosVeiculo> modelosFiltrados = dadosModelos.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos filtrados");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite por favor o código do modelo para buscar os valores de avaliação");
        var codigoModelo = scanner.nextLine();

        endereco = endereco + codigoModelo + "/anos";
        json = httpRequests.sendRequest(endereco);
        List<DadosVeiculo> anos = jsonDataMapper.convertJsonToList(json, DadosVeiculo.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = httpRequests.sendRequest(enderecoAnos);
            Veiculo veiculo = jsonDataMapper.convertJsonToObject(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veículos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);
    }
}