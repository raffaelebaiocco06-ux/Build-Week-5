package Build_week.build_week.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "ruoli")
@Getter
@Setter
@NoArgsConstructor
public class RuoloUtente {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String ruolo;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    public RuoloUtente(String ruolo) {
        this.ruolo = ruolo;
    }
}
