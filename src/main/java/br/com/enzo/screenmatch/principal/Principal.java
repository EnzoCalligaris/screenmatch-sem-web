package br.com.enzo.screenmatch.principal;

import br.com.enzo.screenmatch.model.DadosEpisodios;
import br.com.enzo.screenmatch.model.DadosSerie;
import br.com.enzo.screenmatch.model.DadosTemporada;
import br.com.enzo.screenmatch.model.Episodio;
import br.com.enzo.screenmatch.service.ConsumoApi;
import br.com.enzo.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

        List<DadosEpisodios> dadosEpisodios = dadosTemporadas.stream().flatMap(t -> t.episodios().stream()).collect(Collectors.toList());

        System.out.println("\n Top 5 episódios");
        dadosEpisodios.stream().filter(e -> !e.avaliacao().equalsIgnoreCase("N/A")).sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed()).limit(3).forEach(System.out::println);

        List<Episodio> episodios = dadosTemporadas.stream().flatMap(t -> t.episodios().stream().map(d -> new Episodio(t.temporada(), d))).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("Digite um trecho do título do episódio que quer buscar: ");
        String trechoTitulo = sc.nextLine();

        Optional<Episodio> first = episodios.stream().filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase())).findFirst();

        if (first.isPresent()) {
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " + first);
        }else {
            System.out.println("Episódio não encontrado!");
        }

        System.out.println("A partir de que ano você deseja ver os episódios? ");
        int ano = sc.nextInt();
        sc.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream().filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca)).forEach(e -> System.out.println("Temporada: " + e.getTemporada() + " Episódio: " + e.getTitulo() + " Data lançamento: " + e.getDataLancamento().format(formatador)));


        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);
    }
}