package Build_week.build_week.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "indirizzi")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Indirizzo {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID indirizzoId;

    @Column(nullable = false)
    private String via;

    @Column(nullable = false)
    private String civico;

    @Column(nullable = false)
    private String localita;

    @Column(nullable = false)
    private int cap;

    @OneToOne
    @JoinColumn(nullable = false, name = "comune_id")
    private Comune comune;

    public Indirizzo(String via, String civico, String localita, int cap, Comune comune) {
        this.via = via;
        this.civico = civico;
        this.localita = localita;
        this.cap = cap;
        this.comune = comune;
    }
}
