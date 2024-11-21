package br.com.luarend.TabelaFipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class JsonDataMapper implements IGenericController{
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T convertJsonToObject(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> convertJsonToList(String json, Class<T> tClass) {
        try {
            // Define o tipo de coleção (List<T>)
            CollectionType listType = mapper.getTypeFactory()
                    .constructCollectionType(List.class, tClass);

            return mapper.readValue(json, listType);
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON to list", e);
        }
    }
}
