package br.com.enzo.screenmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodios")

public class Episodio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer temporada;
    private String titulo;
    private Integer numero;
    private LocalDate dataLancamento;
    private Double avaliacao;

    @ManyToOne
    private Serie serie;

    public Episodio() {}

    public Episodio(Integer numTemporada, DadosEpisodios dadosEpisodios) {
        this.temporada = numTemporada;
        this.titulo = dadosEpisodios.titulo();
        this.numero = dadosEpisodios.numEpisodios();
        try {
            this.dataLancamento = LocalDate.parse(dadosEpisodios.dataLancamento());
        }catch (DateTimeParseException e){
            this.dataLancamento = null;
        }

        try {
            this.avaliacao = Double.valueOf(dadosEpisodios.avaliacao());
        } catch (NumberFormatException e) {
            this.avaliacao = 0.0;
        }
    }

    public Long getId() {
        return id;
    }

    public Serie getSerie() {
        return serie;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getNumero() {
        return numero;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Override
    public String toString() {
        return "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numero=" + numero +
                ", dataLancamento=" + dataLancamento +
                ", avaliacao=" + avaliacao;
    }
}
