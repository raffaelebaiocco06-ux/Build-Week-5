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

        if (utenteRepository.existsByUsername(body.username())) {
            throw new BadRequestException("Username già in uso");
        }

        if (utenteRepository.existsByEmail(body.email())) {
            throw new BadRequestException("Email già in uso");
        }

        Utente utente = new Utente(body.username().toLowerCase().trim(), body.email().toLowerCase().trim(), bcrypt.encode(body.password()), body.nome(), body.cognome());

        RuoloUtente ruoloUser = new RuoloUtente("USER");

        ruoloUser.setUtente(utente);
        utente.getRuolo().add(ruoloUser);

        return utenteRepository.save(utente);
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

        found.setUsername(newUsername);
        found.setEmail(newEmail);
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

        Utente found = this.findById(utenteId);

        try {
            Map result = cloudinaryUploader.uploader()
                    .upload(file.getBytes(),
                            ObjectUtils.asMap(
                                    "folder", "avatars",
                                    "resource_type", "image"
                            ));

            log.info("Cloudinary response: {}", result);

            String url = (String) result.get("secure_url");

            if (url == null) {
                throw new RuntimeException("Upload fallito: secure_url nullo");
            }

            found.setAvatar(url);

            return utenteRepository.save(found);

        } catch (IOException e) {
            throw new RuntimeException("Errore upload Cloudinary", e);
        }
    }

    public Utente aggiungiRuolo(UUID utenteId, String nomeRuolo) {

        Utente utente = findById(utenteId);

        boolean esiste = utente.getRuolo()
                .stream()
                .anyMatch(r -> r.getRuolo().equalsIgnoreCase(nomeRuolo));

        if (esiste) {
            throw new BadRequestException("Ruolo già assegnato");
        }

        RuoloUtente ruolo = new RuoloUtente(nomeRuolo.toUpperCase().trim());
        ruolo.setUtente(utente);

        utente.getRuolo().add(ruolo);

        return utenteRepository.save(utente);
    }
}
