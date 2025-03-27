package br.eng.eaa.screenmatch.controller;

import br.eng.eaa.screenmatch.dto.EpisodioDTO;
import br.eng.eaa.screenmatch.dto.SerieDTO;
import br.eng.eaa.screenmatch.model.Categoria;
import br.eng.eaa.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<SerieDTO> obterSeries() {
        return serieService.obterSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series() {
        return serieService.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos() {
        return serieService.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterSeriePorId(@PathVariable Long id) {
        return serieService.obterSeriePorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id) {
        return serieService.obterTodasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{temporada}")
    public List<EpisodioDTO> obterEpisodiosPorTemporada(@PathVariable Long id, @PathVariable Integer temporada) {
        return serieService.obterEpisodiosPorTemporada(id, temporada);
    }

    @GetMapping("/categoria/{categoria}")
    public List<SerieDTO> obterSeriesPorCategoria(@PathVariable String categoria) {
        return serieService.obterSeriesPorCategoria(categoria);
    }

}
