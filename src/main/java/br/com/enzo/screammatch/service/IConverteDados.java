package br.com.enzo.screammatch.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
