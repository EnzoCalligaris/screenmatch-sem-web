package br.com.enzo.screenmatch.service;

import br.com.enzo.screenmatch.dto.EpisodioDTO;
import br.com.enzo.screenmatch.dto.SerieDTO;
import br.com.enzo.screenmatch.model.Categoria;
import br.com.enzo.screenmatch.model.Episodio;
import br.com.enzo.screenmatch.model.Serie;
import br.com.enzo.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repositorio;


    public List<SerieDTO> obterTodasAsSeries(){
        return converteDados(repositorio.findAll());
    }

    public List<SerieDTO> obterSeriesTop5() {
        return converteDados(repositorio.findTop5ByOrderByAvaliacaoDesc());
    }

    private List<SerieDTO> converteDados(List<Serie> series){
        return series.stream().map(s -> new SerieDTO(s.getId(), s.getTitulo(),
                s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(),
                s.getSinops())).collect(Collectors.toList());
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(repositorio.lancamentosMaisRecentes());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = repositorio.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(),
                    s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(),
                    s.getSinops());
        }
        return null;
    }

    public List<EpisodioDTO> obterTodasAsTemporadas(Long id) {
        Optional<Serie> serie = repositorio.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream().map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumero())).collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Long numero) {
        return repositorio.obterEpisodiosPorTemporada(id, numero).stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumero())).collect(Collectors.toList());
    }

    public List<SerieDTO> obterSerisPorCategori(String nomeGenero) {
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        return converteDados(repositorio.findByGenero(categoria));
    }
}
