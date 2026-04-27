package Build_week.build_week.repository;

import Build_week.build_week.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    boolean existsByEmail(String email);
    // ordina
    List<Cliente> findAllByOrderByNomeContattoAsc();
    List<Cliente> findAllByOrderByFatturatoTotAsc();
    List<Cliente> findAllByOrderByDataInserimentoAsc();
    List<Cliente> findAllByOrderByDataUltimoContattoAsc();

    //  TODO provincia della sede legale
    //  List<Cliente>findAllByOrderBy ();

    // filtra
    List<Cliente> findByFatturatoTotBetween(Double minimo, Double massimo);
    List<Cliente> findByDataInserimento(LocalDate data);
    List<Cliente> findByDataUltimoContatto(LocalDate data);
    List<Cliente> findByNomeContattoContainingIgnoreCase(String parteDelNome);

}
