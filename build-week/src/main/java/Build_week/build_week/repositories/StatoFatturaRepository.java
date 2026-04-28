package Build_week.build_week.repositories;

import Build_week.build_week.entities.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatoFatturaRepository extends JpaRepository<StatoFattura, UUID> {
    // Ci servirà per verificare se uno stato esiste già o per cercarlo per nome
    Optional<StatoFattura> findByNomeIgnoreCase(String nome);
}