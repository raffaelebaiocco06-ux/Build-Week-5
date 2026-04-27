package Build_week.build_week.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "fatture")
@Data
@NoArgsConstructor
public class Fattura {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private Double importo;

    @Column(nullable = false, unique = true)
    private Long numero;

    @Column(nullable = false)
    private String stato;

   /* @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente; */

}
