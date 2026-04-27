package Build_week.build_week.entities;

import Build_week.build_week.enums.TipoAzienda;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="clienti")
@Getter
@Setter
@NoArgsConstructor

public class Cliente {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String ragioneSociale;
    @Column(nullable = false)
    private String pIva;
    @Column(nullable = false, unique = true)// da mettere in relazione con utenti
    private String email;
    @Column(nullable = false)
    private LocalDate dataInserimento;
    @Column(nullable = false)
    private LocalDate dataUltimoContatto;
    @Column(nullable = false)
    private long fatturatoTot;
    @Column(nullable = false, unique = true)
    private String pec;
    @Column(nullable = false, unique = true)
    private String telefono;
    @Column(nullable = false, unique = true)
    private String emailContatto;
    @Column(nullable = false)
    private String nomeContatto;
    @Column(nullable = false)
    private String cognomeContatto;
    @Column(nullable = false, unique = true)
    private String telefonoContatto;
    @Column(nullable = false, unique = true)
    private String logoAziendale;
    @Column(nullable = false)
    private TipoAzienda tipoAzienda;
    @Column(nullable = false)
    private Indirizzo indirizzoSedeLegale;
    @Column(nullable = false)
    private Indirizzo indirizzoSedeOperativa;

    public Cliente(String ragioneSociale, String pIva, String email, LocalDate dataInserimento, LocalDate dataUltimoContatto, long fatturatoTot, String pec, String telefono, String nomeContatto, String emailContatto, String cognomeContatto, String telefonoContatto, String logoAziendale, TipoAzienda tipoAzienda, Indirizzo indirizzoSedeLegale, Indirizzo indirizzoSedeOperativa) {
        this.ragioneSociale = ragioneSociale;
        this.pIva = pIva;
        this.email = email;
        this.dataInserimento = dataInserimento;
        this.dataUltimoContatto = dataUltimoContatto;
        this.fatturatoTot = fatturatoTot;
        this.pec = pec;
        this.telefono = telefono;
        this.nomeContatto = nomeContatto;
        this.emailContatto = emailContatto;
        this.cognomeContatto = cognomeContatto;
        this.telefonoContatto = telefonoContatto;
        this.logoAziendale = logoAziendale;
        this.tipoAzienda = tipoAzienda;
        this.indirizzoSedeLegale = indirizzoSedeLegale;
        this.indirizzoSedeOperativa = indirizzoSedeOperativa;
    }
}
