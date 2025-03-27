package br.eng.eaa.screenmatch.service;

import br.eng.eaa.screenmatch.dto.SerieDTO;
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
        return converterDados(serieRepository.findTop5ByOrderByEpisodiosDataLancamentoDesc());
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
}
