package br.com.enzo.screenmatch.principal;

import br.com.enzo.screenmatch.model.DadosEpisodios;
import br.com.enzo.screenmatch.model.DadosSerie;
import br.com.enzo.screenmatch.model.DadosTemporada;
import br.com.enzo.screenmatch.service.ConsumoApi;
import br.com.enzo.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=db2a7b69";

    public void exibeMenu(){
        System.out.println("Digite o nome da série para buscar: ");
        String nomeSerie = sc.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);


		List<DadosTemporada> dadosTemporadas = new ArrayList<>();

		for (int i = 1; i<=dados.totalTemporadas(); i++){
			json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+")+ "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			dadosTemporadas.add(dadosTemporada);
        }
		dadosTemporadas.forEach(System.out::println);

        dadosTemporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

    }
}
