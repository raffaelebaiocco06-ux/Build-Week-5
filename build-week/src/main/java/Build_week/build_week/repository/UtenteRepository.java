package Build_week.build_week.repository;

import Build_week.build_week.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
