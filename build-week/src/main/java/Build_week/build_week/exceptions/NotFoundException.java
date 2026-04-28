package Build_week.build_week.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Risorsa con id " + id + " non trovata");
    }

    public NotFoundException(String message) {
        super(message);
    }
}