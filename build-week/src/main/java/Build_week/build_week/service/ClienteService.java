package Build_week.build_week.service;

import Build_week.build_week.entities.Cliente;
import Build_week.build_week.entities.Indirizzo;
import Build_week.build_week.exceptions.BadRequestException;
import Build_week.build_week.exceptions.NotFoundException;
import Build_week.build_week.payload.ClienteDTO;
import Build_week.build_week.repository.ClienteRepository;
import Build_week.build_week.repository.IndirizzoRepository;
import Build_week.build_week.specification.ClienteSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final IndirizzoRepository indirizzoRepository;

    public ClienteService(ClienteRepository clienteRepository, IndirizzoRepository indirizzoRepository) {
        this.clienteRepository = clienteRepository;
        this.indirizzoRepository = indirizzoRepository;
    }

    public Cliente salva(ClienteDTO body){
        if(this.clienteRepository.existsByEmail(body.email())) throw new BadRequestException("L'indirizzo email " + body.email() + " è già in uso!");

        // qui ho da cerca la classe

        Indirizzo sedeLegale= indirizzoRepository.findById(body.indirizzoSedeLegaleId()).orElseThrow(()-> new NotFoundException("Indirizzo sede legale non trovato"));
        Indirizzo sedeOperativa= indirizzoRepository.findById(body.indirizzoSedeOperativaId()).orElseThrow(()-> new NotFoundException("Indirizzo sede operativa non trovato"));

        Cliente nuovocliente= new Cliente(body.ragioneSociale(),body.pIva(),body.email(),body.dataInserimento(),body.dataUltimoContatto(),body.fatturatoTot(),body.pec(),body.telefono(),body.emailContatto(),body.nomeContatto(),body.cognomeContatto(),body.telefonoContatto(),body.logoAziendale(),body.tipoAzienda(),sedeLegale,sedeOperativa);
        return clienteRepository.save(nuovocliente);
}
    public Cliente findById(UUID id){
        return clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Utente con id " + id + " non trovato"));
    }

    public void findByIdAndDelete(UUID id){
        Cliente trovato = this.findById(id);
        this.clienteRepository.delete(trovato);
    }

    // qui vo con le specification

    public Page<Cliente> cercaClienti(
            String nome,
            Long minFatturato,
            Long maxFatturato,
            LocalDate dataInserimento,
            LocalDate dataUltimoContatto,
            int page, int size, String sortBy) {


        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Specification<Cliente> spec = Specification.where( (root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        if (nome != null) {
            spec = spec.and(ClienteSpecification.nomeContatto(nome));
        }

        if (minFatturato != null || maxFatturato != null) {
            spec = spec.and(ClienteSpecification.FatturatoBeetwen(minFatturato, maxFatturato));
        }

        if (dataInserimento != null) {
            spec = spec.and(ClienteSpecification.dataInserimento(dataInserimento));
        }

        if (dataUltimoContatto != null) {
            spec = spec.and(ClienteSpecification.dataUltimoContatto(dataUltimoContatto));
        }

        return clienteRepository.findAll(spec, pageable);
    }
}


