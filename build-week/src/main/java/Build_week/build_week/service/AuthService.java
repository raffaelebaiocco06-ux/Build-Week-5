package Build_week.build_week.service;

import Build_week.build_week.entities.Utente;
import Build_week.build_week.exceptions.NotFoundException;
import Build_week.build_week.exceptions.UnauthorizedException;
import Build_week.build_week.payload.LoginDTO;
import Build_week.build_week.security.TokenTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UtenteService utenteService;
    private final TokenTools tokenTools;
    private final PasswordEncoder bcrypt;

    public AuthService(UtenteService utenteService, TokenTools tokenTools, PasswordEncoder bcrypt) {

        this.utenteService = utenteService;
        this.tokenTools = tokenTools;
        this.bcrypt = bcrypt;
    }

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        try {
            Utente found = this.utenteService.findByUsername(body.email());
            if (this.bcrypt.matches(body.password(), found.getPassword())) {
                return this.tokenTools.generateToken(found);
            } else {
                throw new UnauthorizedException("Credenziali errate");
            }
        } catch (NotFoundException ex) {
            throw new UnauthorizedException("Credenziali errate");
        }
    }
}
