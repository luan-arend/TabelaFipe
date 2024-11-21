package br.com.luarend.TabelaFipe.principal;

import br.com.luarend.TabelaFipe.model.DadosVeiculo;
import br.com.luarend.TabelaFipe.service.HttpRequests;
import br.com.luarend.TabelaFipe.service.JsonDataMapper;

import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private HttpRequests httpRequests = new HttpRequests();
    private JsonDataMapper jsonDataMapper = new JsonDataMapper();

    private final String URL = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu(){
        System.out.println("Digite o que deseja buscar: " +
                "Carros / Caminhao / Moto");

        var tipoVeiculo = scanner.nextLine();

        var json = httpRequests.sendRequest(URL + tipoVeiculo + "/marcas/");
        var dadosVeiculos = jsonDataMapper.convertJsonToList(json, DadosVeiculo.class);
        System.out.println(dadosVeiculos);

    }
}
