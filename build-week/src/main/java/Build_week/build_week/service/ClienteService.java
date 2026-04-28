package Build_week.build_week.service;

import Build_week.build_week.entities.Cliente;
import Build_week.build_week.entities.Indirizzo;
import Build_week.build_week.exceptions.BadRequestException;
import Build_week.build_week.exceptions.NotFoundException;
import Build_week.build_week.payload.ClienteDTO;
import Build_week.build_week.repository.ClienteRepository;
import Build_week.build_week.repository.IndirizzoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Cliente> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return clienteRepository.findAll(pageable);
    }
    public Cliente findById(UUID id){
        return clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Utente con id " + id + " non trovato"));
    }


    public List<Cliente> ordinaPerNome() {
        return clienteRepository.findAllByOrderByNomeContattoAsc();
    }

    public List<Cliente> ordinaPerFatturato() {
        return clienteRepository.findAllByOrderByFatturatoTotAsc();
    }

    public List<Cliente> ordinaPerDataInserimento() {
        return clienteRepository.findAllByOrderByDataInserimentoAsc();
    }

    public List<Cliente> ordinaPerDataUltimoContatto() {
        return clienteRepository.findAllByOrderByDataUltimoContattoAsc();
    }

    public List<Cliente> filtraPerFatturato(Double minimo, Double massimo) {
        if (minimo == null || massimo == null) {
            return Collections.emptyList();
        }
        return clienteRepository.findByFatturatoTotBetween(minimo, massimo);
    }

    public List<Cliente> filtraPerDataInserimento(LocalDate data) {
        if (data == null) {
            return Collections.emptyList();
        }
        return clienteRepository.findByDataInserimento(data);
    }

    public List<Cliente> filtraPerDataUltimoContatto(LocalDate data) {
        if (data == null) {
            return Collections.emptyList();
        }
        return clienteRepository.findByDataUltimoContatto(data);
    }

    public List<Cliente> cercaClientiPerNome(String parteNome) {
        if (parteNome == null || parteNome.isBlank()) {
            return Collections.emptyList();
        }
        return clienteRepository.findByNomeContattoContainingIgnoreCase(parteNome);
    }

}
