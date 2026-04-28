package Build_week.build_week.payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record FatturaDTO(
        @NotNull(message = "La data è obbligatoria")
        LocalDate data,

        @NotNull(message = "L'importo è obbligatorio")
        Double importo,

        @NotNull(message = "Il numero fattura è obbligatorio")
        Long numero,

        @NotNull(message = "L'ID dello stato è obbligatorio")
        UUID statoId
) {
}