package Build_week.build_week.repository;

import Build_week.build_week.entities.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, UUID>, JpaSpecificationExecutor<Fattura> {
}