package br.com.enzo.screenmatch.service;

import br.com.enzo.screenmatch.dto.SerieDTO;
import br.com.enzo.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repositorio;


    public List<SerieDTO> obterTodasAsSeries(){
        return repositorio.findAll().stream().map(s -> new SerieDTO(s.getId(), s.getTitulo(),
                s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(),
                s.getSinops())).collect(Collectors.toList());
    }
}
