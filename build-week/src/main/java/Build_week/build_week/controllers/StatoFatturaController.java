package Build_week.build_week.controllers;

import Build_week.build_week.entities.StatoFattura;
import Build_week.build_week.service.StatoFatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stati-fattura")
public class StatoFatturaController {

    @Autowired
    private StatoFatturaService statoFatturaService;

    @GetMapping
    public List<StatoFattura> getAll() {
        return statoFatturaService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public StatoFattura create(@RequestBody StatoFattura body) {
        return statoFatturaService.save(body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable UUID id) {
        statoFatturaService.delete(id);
    }
}