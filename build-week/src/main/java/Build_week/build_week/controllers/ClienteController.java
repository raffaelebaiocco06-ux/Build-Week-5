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
    public Page<Cliente> findAll(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "nomeContatto") String sortBy) {
        return clienteService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody @Validated ClienteDTO body) {
        return clienteService.salva(body);
    }

    @GetMapping("/{id}")
    public Cliente findById(@PathVariable UUID id) {
        return clienteService.findById(id);
    }

    @GetMapping("/ordina/nome")
    public List<Cliente> ordinaPerNome() {
        return clienteService.ordinaPerNome();
    }

    @GetMapping("/ordina/fatturato")
    public List<Cliente> ordinaPerFatturato() {
        return clienteService.ordinaPerFatturato();
    }

    @GetMapping("/ordina/data-inserimento")
    public List<Cliente> ordinaPerDataInserimento() {
        return clienteService.ordinaPerDataInserimento();
    }

    @GetMapping("/ordina/ultimo-contatto")
    public List<Cliente> ordinaPerDataUltimoContatto() {
        return clienteService.ordinaPerDataUltimoContatto();
    }

    @GetMapping("/ordina/provincia-sede-legale")
    public List<Cliente> ordinaperProvinciadellaSedeLegale(){
        return clienteService.ordinaPerProvinciaSedeLegale();
    }



    @GetMapping("/filtra/fatturato")
    public List<Cliente> filtraPerFatturato(@RequestParam Double min, @RequestParam Double max) {
        return clienteService.filtraPerFatturato(min, max);
    }

    @GetMapping("/filtra/data-inserimento")
    public List<Cliente> filtraPerDataInserimento(@RequestParam LocalDate data) {
        return clienteService.filtraPerDataInserimento(data);
    }

    @GetMapping("/filtra/ultimo-contatto")
    public List<Cliente> filtraPerDataUltimoContatto(@RequestParam LocalDate data) {
        return clienteService.filtraPerDataUltimoContatto(data);
    }

    @GetMapping("/cerca/nome")
    public List<Cliente> cercaPerNome(@RequestParam String nome) {
        return clienteService.cercaClientiPerNome(nome);
    }
    //manca la delete

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteC(@PathVariable UUID id){
        clienteService.findByIdAndDelete(id);
    }
}

