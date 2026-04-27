package Build_week.build_week.payload;

import Build_week.build_week.enums.TipoAzienda;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteDTO(

        @NotBlank(message = "La ragione sociale è obbligatoria")
        @Size(min = 2, max = 100, message = "La ragione sociale deve avere tra 2 e 100 caratteri")
        String ragioneSociale,

        @NotBlank(message = "La partita IVA è obbligatoria")
        @Size(min = 11, max = 11, message = "La partita IVA deve avere 11 caratteri")
        String pIva,

        @NotBlank(message = "L'email aziendale è obbligatoria")
        @Email(message = "Email non valida")
        String email,

        @NotNull(message = "La data di inserimento è obbligatoria")
        LocalDate dataInserimento,

        @NotNull(message = "La data ultimo contatto è obbligatoria")
        LocalDate dataUltimoContatto,

        @NotNull(message = "Il fatturato è obbligatorio")
        @PositiveOrZero(message = "Il fatturato non può essere negativo")
        Long fatturatoTot,

        @NotBlank(message = "La PEC è obbligatoria")
        @Email(message = "PEC non valida")
        String pec,

        @NotBlank(message = "Il telefono è obbligatorio")
        String telefono,

        @NotBlank(message = "L'email del contatto è obbligatoria")
        @Email(message = "Email contatto non valida")
        String emailContatto,

        @NotBlank(message = "Il nome del contatto è obbligatorio")
        @Size(min = 2, max = 30, message = "Il nome deve avere tra 2 e 30 caratteri")
        String nomeContatto,

        @NotBlank(message = "Il cognome del contatto è obbligatorio")
        @Size(min = 2, max = 30, message = "Il cognome deve avere tra 2 e 30 caratteri")
        String cognomeContatto,

        @NotBlank(message = "Il telefono del contatto è obbligatorio")
        String telefonoContatto,

        @NotBlank(message = "Il logo aziendale è obbligatorio")
        String logoAziendale,

        @NotNull(message = "Il tipo azienda è obbligatorio")
        TipoAzienda tipoAzienda,

        @NotNull(message = "L'indirizzo della sede legale è obbligatorio")
        UUID indirizzoSedeLegaleId,

        @NotNull(message = "L'indirizzo della sede operativa è obbligatorio")
        UUID indirizzoSedeOperativaId






) {
}
