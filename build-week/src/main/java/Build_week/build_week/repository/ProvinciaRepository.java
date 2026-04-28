package Build_week.build_week.repository;

import Build_week.build_week.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, UUID> {
    Optional<Provincia> findBySigla(String sigla);
}
