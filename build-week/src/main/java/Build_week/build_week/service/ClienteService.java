package Build_week.build_week.service;

import Build_week.build_week.entities.Cliente;
import Build_week.build_week.exceptions.BadRequestException;
import Build_week.build_week.exceptions.NotFoundException;
import Build_week.build_week.payload.ClienteDTO;
import Build_week.build_week.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

public Cliente salva(ClienteDTO body){
        if(this.clienteRepository.existsByEmail(body.email())) throw new BadRequestException("L'indirizzo email " + body.email() + " è già in uso!");

        //trovare la classe di indirizzo
        //chiedere nicola
        Cliente nuovocliente= new Cliente(body.ragioneSociale(),body.pIva(),body.email(),body.dataInserimento(),body.dataUltimoContatto(),body.fatturatoTot(),body.pec(),body.telefono(),body.emailContatto(),body.nomeContatto(),body.cognomeContatto(),body.telefonoContatto(),body.logoAziendale(),body.tipoAzienda(),body.indirizzoSedeLegaleId(),body.indirizzoSedeOperativaId());
        return clienteRepository.save(nuovocliente);
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
