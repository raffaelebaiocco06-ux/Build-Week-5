package Build_week.build_week.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record IndirizzoDTO(
        @NotBlank(message = "La via è un campo obbligatorio e non può essere una Stringa vuota")
        @Size(min = 2, message = "La via deve avere almeno 2 caratteri")
        String via,
        @NotBlank(message = "Il civico è un campo obbligatorio e non può essere una Stringa vuota")
        @Size(min = 1, max = 10, message = "Il civico deve essere compreso tra 1 e 10 caratteri")
        String civico,
        @NotBlank(message = "La località è un campo obbligatorio e non può essere una Stringa vuota")
        @Size(min = 2, max = 30, message = "La località deve essere compreso tra 2 e 30 caratteri")
        String localita,
        @NotNull(message = "Il CAP è obbligatorio")
        int cap,
        @NotNull(message = "L'id del comune è obbligatorio")
        UUID comuneId
) {
}
