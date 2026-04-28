package Build_week.build_week.services;

import Build_week.build_week.entities.Fattura;
import Build_week.build_week.exceptions.NotFoundException;
import Build_week.build_week.repositories.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class FatturaService {

    @Autowired
    private FatturaRepository fatturaRepository;

    public Fattura save(Fattura fattura) {
        return fatturaRepository.save(fattura);
    }

    public Page<Fattura> findAll(Pageable pageable) {
        return fatturaRepository.findAll(pageable);
    }

    public Fattura findById(UUID id) {
        return fatturaRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void delete(UUID id) {
        Fattura f = this.findById(id);
        fatturaRepository.delete(f);
    }

    public Page<Fattura> findByCliente(UUID clienteId, Pageable pageable) {
        return fatturaRepository.findByClienteId(clienteId, pageable);
    }

    public Page<Fattura> findByStato(String nomeStato, Pageable pageable) {
        return fatturaRepository.findByStato_NomeIgnoreCase(nomeStato, pageable);
    }

    public Page<Fattura> findByData(LocalDate data, Pageable pageable) {
        return fatturaRepository.findByData(data, pageable);
    }

    public Page<Fattura> findByAnno(int anno, Pageable pageable) {
        return fatturaRepository.findByAnno(anno, pageable);
    }

    public Page<Fattura> findByRangeImporto(Double min, Double max, Pageable pageable) {
        return fatturaRepository.findByImportoBetween(min, max, pageable);
    }
}