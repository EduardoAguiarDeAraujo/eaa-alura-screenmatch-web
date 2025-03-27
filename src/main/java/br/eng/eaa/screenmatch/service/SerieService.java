package br.eng.eaa.screenmatch.service;

import br.eng.eaa.screenmatch.dto.EpisodioDTO;
import br.eng.eaa.screenmatch.dto.SerieDTO;
import br.eng.eaa.screenmatch.model.Categoria;
import br.eng.eaa.screenmatch.model.Serie;
import br.eng.eaa.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> obterSeries() {
        return converterDados(serieRepository.findAll());
    }

    public List<SerieDTO> obterTop5Series() {
        return converterDados(serieRepository.findTop5ByOrderByAvaliacaoDesc());
    }

    public List<SerieDTO> obterLancamentos() {
        return converterDados(serieRepository.lancamentosMaisRecentes());
    }

    private List<SerieDTO> converterDados(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(),s.getTitulo(),s.getTotalTemporadas(), s.getAvaliacao(),
                        s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }

    public SerieDTO obterSeriePorId(Long id) {
        return serieRepository.findById(id)
                .map(s -> new SerieDTO(s.getId(),s.getTitulo(),s.getTotalTemporadas(), s.getAvaliacao(),
                        s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
                .orElseThrow(() -> new RuntimeException("Série não encontrada"));
    }

    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        return serieRepository.findById(id)
                .map(s -> s.getEpisodios().stream()
                        .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException("Série não encontrada"));
    }

    public List<EpisodioDTO> obterEpisodiosPorTemporada(Long id, Integer temporada) {
        return serieRepository.findById(id)
                .map(s -> s.getEpisodios().stream()
                        .filter(e -> e.getTemporada().equals(temporada))
                        .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException("Série não encontrada"));

    }

    public List<SerieDTO> obterSeriesPorCategoria(String categoriaNome) {
        Categoria categoria = Categoria.fromPortugues(categoriaNome);
        return converterDados(serieRepository.findByGenero(categoria));
    }
}
