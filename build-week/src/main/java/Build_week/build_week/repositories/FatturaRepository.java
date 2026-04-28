package Build_week.build_week.repositories;

import Build_week.build_week.entities.Fattura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, UUID> {

    Page<Fattura> findByClienteId(UUID clienteId, Pageable pageable);

    Page<Fattura> findByStato_NomeIgnoreCase(String nomeStato, Pageable pageable);

    Page<Fattura> findByData(LocalDate data, Pageable pageable);

    @Query("SELECT f FROM Fattura f WHERE YEAR(f.data) = :anno")
    Page<Fattura> findByAnno(@Param("anno") int anno, Pageable pageable);

    Page<Fattura> findByImportoBetween(Double min, Double max, Pageable pageable);

    Page<Fattura> findByStatoIgnoreCase(String stato, Pageable pageable);
}