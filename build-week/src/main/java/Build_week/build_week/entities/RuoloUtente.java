package Build_week.build_week.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(nullable = false)
    private String ruolo;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    @JsonBackReference
    private Utente utente;

    public RuoloUtente(String ruolo) {
        this.ruolo = ruolo;
    }
}
