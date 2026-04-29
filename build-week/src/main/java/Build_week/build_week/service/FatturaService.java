package Build_week.build_week.service;

import Build_week.build_week.entities.Fattura;
import Build_week.build_week.entities.StatoFattura;
import Build_week.build_week.exceptions.NotFoundException;
import Build_week.build_week.payload.FatturaDTO;
import Build_week.build_week.repository.FatturaRepository;
import Build_week.build_week.specification.FatturaSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class FatturaService {

    @Autowired
    private FatturaRepository fatturaRepository;

    @Autowired
    private StatoFatturaService statoFatturaService;

    public Fattura save(FatturaDTO body) {
        StatoFattura stato = statoFatturaService.findById(body.statoId());
        Fattura nuovaFattura = new Fattura();
        nuovaFattura.setData(body.data());
        nuovaFattura.setImporto(body.importo());
        nuovaFattura.setNumero(body.numero());
        nuovaFattura.setStato(stato);
        return fatturaRepository.save(nuovaFattura);
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

    public Page<Fattura> filtraFatture(UUID clienteId, String stato, Integer anno, LocalDate data, Double min, Double max, Pageable pageable) {
        Specification<Fattura> spec = Specification.where((root, query, cb) -> cb.conjunction());

        if (clienteId != null) spec = spec.and(FatturaSpecifications.hasCliente(clienteId));
        if (stato != null) spec = spec.and(FatturaSpecifications.hasStato(stato));
        if (anno != null) spec = spec.and(FatturaSpecifications.hasAnno(anno));
        if (data != null) spec = spec.and(FatturaSpecifications.hasData(data));

        if (min != null || max != null) {
            spec = spec.and(FatturaSpecifications.hasImportoBetween(min, max));
        }

        return fatturaRepository.findAll(spec, pageable);
    }
}