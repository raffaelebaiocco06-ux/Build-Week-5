package Build_week.build_week.service;

import Build_week.build_week.repository.UtenteRepository;
import com.cloudinary.Cloudinary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final Cloudinary cloudinaryUploader;

    public UtenteService(UtenteRepository utenteRepository, Cloudinary cloudinaryUploader) {
        this.utenteRepository = utenteRepository;
        this.cloudinaryUploader = cloudinaryUploader;
    }
}
