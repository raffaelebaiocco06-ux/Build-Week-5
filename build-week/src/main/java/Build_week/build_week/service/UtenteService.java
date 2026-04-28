package Build_week.build_week.service;

import Build_week.build_week.entities.RuoloUtente;
import Build_week.build_week.entities.Utente;
import Build_week.build_week.exceptions.BadRequestException;
import Build_week.build_week.exceptions.NotFoundException;
import Build_week.build_week.payload.UtenteDTO;
import Build_week.build_week.repository.UtenteRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

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

        Utente newUtente = new Utente(username, email, this.bcrypt.encode(body.password()), body.nome(), body.cognome());
        Utente savedUtente = this.utenteRepository.save(newUtente);

        log.info("L'utente con id " + savedUtente.getId() + " è stato salvato correttamente");

        return savedUtente;
    }

    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (size > 100 || size < 0) size = 10;
        if (page < 10) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utenteRepository.findAll(pageable);
    }

    public Utente findById(UUID utenteId) {
        return this.utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }

    public Utente findByUsername(String username) {
        return this.utenteRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Username non trovato"));
    }

    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email non trovato"));
    }

    public Utente findByIdAndUpdate(UUID utenteId, UtenteDTO body) {

        Utente found = this.findById(utenteId);

        String newUsername = body.username().toLowerCase().trim();
        String newEmail = body.email().toLowerCase().trim();

        if (!found.getUsername().equals(body.username())) {

            if (this.utenteRepository.existsByUsername(body.username()))
                throw new BadRequestException("L'username " + body.username() + "è gia in uso");
        }

        if (!found.getEmail().equals(body.email())) {

            if (this.utenteRepository.existsByEmail(body.email()))
                throw new BadRequestException("L'email " + body.email() + "è gia in uso");
        }

        found.setUsername(body.username());
        found.setEmail(body.email());
        found.setNome(body.nome());
        found.setCognome(body.cognome());

        if (body.password() != null && !body.password().isBlank()) {
            String nuovaPassword = this.bcrypt.encode(body.password());
            found.setPassword(nuovaPassword);
        }

        if (body.avatar() != null && !body.avatar().isBlank()) {
            found.setAvatar(body.avatar());
        }

        Utente updateUtente = this.utenteRepository.save(found);

        log.info("L'utente " + updateUtente.getId() + "è stato aggiornato correttamente");

        return updateUtente;
    }

    public void findByIdAndDelete(UUID utenteId) {
        Utente found = this.findById(utenteId);
        this.utenteRepository.delete(found);
    }

    public Utente avatarUpload(MultipartFile file, UUID utenteId) {

        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File non valido o vuoto");
        }

        if (file.getSize() > 2 * 1024 * 1024) {
            throw new BadRequestException("File troppo grande (max 2MB)");
        }

        String contentType = file.getContentType();
        if (contentType == null ||
                !(contentType.equals("image/png") ||
                        contentType.equals("image/jpeg") ||
                        contentType.equals("image/gif"))) {
            throw new BadRequestException("Formato file non supportato");
        }

        Utente found = this.findById(utenteId);

        try {
            Map result = cloudinaryUploader.uploader()
                    .upload(file.getBytes(), ObjectUtils.emptyMap());

            String url = (String) result.get("secure_url");

            found.setAvatar(url);

            Utente updatedUtente = this.utenteRepository.save(found);

            log.info("Avatar aggiornato per il dipendente con id " + updatedUtente.getId());

            return updatedUtente;

        } catch (IOException e) {
            throw new RuntimeException("Errore durante upload avatar", e);
        }
    }

    public Utente promuoviAdAdmin(UUID utenteId) {

        Utente utente = this.findById(utenteId);

        if (!utente.getRuoli().contains("ROLE_ADMIN")) {
            utente.setRuoli(utente.getRuoli() + ",ROLE_ADMIN");
        }

        return this.utenteRepository.save(utente);
    }

//    public Utente aggiungiRuolo(UUID utenteId, String nuovoRuolo) {
//
//        Utente utente = this.findById(utenteId);
//
//        String ruoloFormattato = "ROLE_" + nuovoRuolo.toUpperCase();
//
//        if (!utente.getRuoli().contains(ruoloFormattato)) {
//            utente.getRuoli().add(ruoloFormattato);
//        }
//
//        return this.utenteRepository.save(utente);
//    }
}
