package br.com.luarend.TabelaFipe.principal;

import br.com.luarend.TabelaFipe.model.DadosModelo;
import br.com.luarend.TabelaFipe.model.DadosVeiculo;
import br.com.luarend.TabelaFipe.service.HttpRequests;
import br.com.luarend.TabelaFipe.service.JsonDataMapper;

import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;

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
    }
}
