package Build_week.build_week.entities;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "province")
@NoArgsConstructor
@Setter
@ToString
public class Provincia {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID provinciaId;

    @Getter
    @CsvBindByName(column = "Sigla")
    private String sigla;

    @Getter
    @CsvBindByName(column = "Provincia")
    private String nome;

    @Getter
    @CsvBindByName(column = "Regione")
    private String regione;

    public Provincia(String nome, String sigla, String regione) {
        this.nome = nome;
        this.sigla = sigla;
        this.regione = regione;
    }
}
