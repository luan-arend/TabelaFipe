package br.com.luarend.TabelaFipe.service;

public interface IGenericController {
    <T> T convertJsonToObject(String json, Class<T> tClass);
}
