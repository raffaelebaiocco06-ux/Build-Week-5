package Build_week.build_week.service;

import Build_week.build_week.entities.Comune;
import Build_week.build_week.entities.Indirizzo;
import Build_week.build_week.exceptions.NotFoundException;
import Build_week.build_week.payload.IndirizzoDTO;
import Build_week.build_week.repository.IndirizzoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class IndirizzoService {
    private final IndirizzoRepository indirizzoRepository;
    private final ComuneService comuneService;

    public IndirizzoService(IndirizzoRepository indirizzoRepository, ComuneService comuneService) {
        this.indirizzoRepository = indirizzoRepository;
        this.comuneService = comuneService;
    }

    public Indirizzo saveIndirizzo(IndirizzoDTO body) {
        Comune comune = this.comuneService.findComuneById(body.comuneId());
        Indirizzo indirizzo = new Indirizzo(body.via(), body.civico(), body.localita(), body.cap(), comune);
        this.indirizzoRepository.save(indirizzo);
        log.info("L'indirizzo è stato salvato con successo");
        return indirizzo;
    }

    public Indirizzo findIndirizzoById(UUID indirizzoId) {
        return this.indirizzoRepository.findById(indirizzoId).orElseThrow(() -> new NotFoundException(indirizzoId));
    }

    public Page<Indirizzo> findAll(int page, int size, String sortBy) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.indirizzoRepository.findAll(pageable);
    }
}
