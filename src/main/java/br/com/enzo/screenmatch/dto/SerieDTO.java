package br.com.enzo.screenmatch.dto;

import br.com.enzo.screenmatch.model.Categoria;


public record SerieDTO( Long id, String titulo,
 Integer totalTemporadas,
 Double avaliacao,
 Categoria genero,
 String atores,
 String poster,
 String sinops) {
}
