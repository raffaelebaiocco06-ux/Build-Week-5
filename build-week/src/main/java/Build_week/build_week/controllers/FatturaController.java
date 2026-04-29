package Build_week.build_week.controllers;

import Build_week.build_week.entities.Fattura;
import Build_week.build_week.payload.FatturaDTO;
import Build_week.build_week.service.FatturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/fatture")
public class FatturaController {

    @Autowired
    private FatturaService fatturaService;

    @GetMapping
    public Page<Fattura> getAll(Pageable pageable) {
        return fatturaService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Fattura getById(@PathVariable UUID id) {
        return fatturaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura create(@RequestBody @Validated FatturaDTO body) {
        return fatturaService.save(body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable UUID id) {
        fatturaService.delete(id);
    }
    
    @GetMapping("/search")
    public Page<Fattura> search(
            @RequestParam(required = false) UUID clienteId,
            @RequestParam(required = false) String stato,
            @RequestParam(required = false) Integer anno,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max,
            Pageable pageable) {
        return fatturaService.filtraFatture(clienteId, stato, anno, data, min, max, pageable);
    }
}