package Build_week.build_week.services;

import Build_week.build_week.entities.StatoFattura;
import Build_week.build_week.exceptions.NotFoundException;
import Build_week.build_week.repositories.StatoFatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StatoFatturaService {

    @Autowired
    private StatoFatturaRepository statoFatturaRepository;

    public StatoFattura save(StatoFattura body) {
        return statoFatturaRepository.save(body);
    }

    public List<StatoFattura> findAll() {
        return statoFatturaRepository.findAll();
    }

    public StatoFattura findById(UUID id) {
        return statoFatturaRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public StatoFattura findByNome(String nome) {
        return statoFatturaRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new NotFoundException("Stato con nome " + nome + " non trovato"));
    }

    public void delete(UUID id) {
        StatoFattura found = this.findById(id);
        statoFatturaRepository.delete(found);
    }
}