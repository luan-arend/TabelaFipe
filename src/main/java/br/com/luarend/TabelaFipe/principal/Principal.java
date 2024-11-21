package br.com.luarend.TabelaFipe.principal;

import br.com.luarend.TabelaFipe.model.DadosVeiculo;
import br.com.luarend.TabelaFipe.service.HttpRequests;
import br.com.luarend.TabelaFipe.service.JsonDataMapper;

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

        var tipoVeiculo = scanner.nextLine();
        String endereco;

        if (tipoVeiculo.toLowerCase().contains("carr")) {
            endereco = URL_BASE + "carros/marcas/";
        } else if (tipoVeiculo.toLowerCase().contains("caminh")) {
            endereco = URL_BASE + "caminhoes/marcas/";
        } else if (tipoVeiculo.toLowerCase().contains("moto")) {
            endereco = URL_BASE + "motos/marcas/";
        } else {
            System.out.println("Opção inválida, tente novamente!");
            return;
        }

        var json = httpRequests.sendRequest(endereco);
        var dadosVeiculos = jsonDataMapper.convertJsonToList(json, DadosVeiculo.class);
        System.out.println(dadosVeiculos);

    }
}
