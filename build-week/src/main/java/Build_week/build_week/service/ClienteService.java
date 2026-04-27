package Build_week.build_week.service;

import Build_week.build_week.entities.Cliente;
import Build_week.build_week.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
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
