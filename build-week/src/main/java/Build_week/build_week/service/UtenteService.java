package Build_week.build_week.service;

import Build_week.build_week.entities.Utente;
import Build_week.build_week.exceptions.BadRequestException;
import Build_week.build_week.payload.UtenteDTO;
import Build_week.build_week.repository.UtenteRepository;
import com.cloudinary.Cloudinary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final Cloudinary cloudinaryUploader;
    private final PasswordEncoder bcrypt;

    public UtenteService(UtenteRepository utenteRepository, Cloudinary cloudinaryUploader, PasswordEncoder bcrypt) {
        this.utenteRepository = utenteRepository;
        this.cloudinaryUploader = cloudinaryUploader;
        this.bcrypt = bcrypt;
    }

    public Utente save(UtenteDTO body) {

        if (this.utenteRepository.existsByUsername(body.username())) {
            throw new BadRequestException("L'username " + body.username() + " è già in uso");
        }

        if (this.utenteRepository.existsByEmail(body.email())) {
            throw new BadRequestException("L'email " + body.email() + " è già in uso");
        }

        String username = body.username().toLowerCase().trim();
        String email = body.email().toLowerCase().trim();

        Utente newUtente = new Utente(body.username(), body.email(), this.bcrypt.encode(body.password()), body.nome(), body.cognome());
        Utente savedUtente = this.utenteRepository.save(newUtente);

        log.info("L'utente con id " + savedUtente.getId() + " è stato salvato correttamente");

        return savedUtente;
    }
}
