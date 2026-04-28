package Build_week.build_week.controllers;

import Build_week.build_week.entities.Utente;
import Build_week.build_week.exceptions.ValidationException;
import Build_week.build_week.payload.LoginDTO;
import Build_week.build_week.payload.LoginRespDTO;
import Build_week.build_week.payload.UtenteDTO;
import Build_week.build_week.payload.UtenteRespDTO;
import Build_week.build_week.service.AuthService;
import Build_week.build_week.service.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UtenteService utenteService;

    public AuthController(AuthService authService, UtenteService utenteService) {

        this.authService = authService;
        this.utenteService = utenteService;
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        return new LoginRespDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public UtenteRespDTO saveUser(@RequestBody @Validated UtenteDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }

        Utente newUtente = this.utenteService.save(body);
        return new UtenteRespDTO(newUtente.getId());
    }
}
