package Build_week.build_week.controllers;

import Build_week.build_week.entities.Cliente;
import Build_week.build_week.payload.ClienteDTO;
import Build_week.build_week.service.ClienteService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clienti")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public Page<Cliente> getAllClienti(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long minFatturato,
            @RequestParam(required = false) Long maxFatturato,
            @RequestParam(required = false) LocalDate dataInserimento,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ragioneSociale") String sortBy) {

        return clienteService.cercaClienti(nome, minFatturato, maxFatturato, dataInserimento, null, page, size, sortBy);
    }
}

