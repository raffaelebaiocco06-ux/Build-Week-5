package Build_week.build_week.controllers;

import Build_week.build_week.entities.Fattura;
import Build_week.build_week.payloads.FatturaDTO;
import Build_week.build_week.services.FatturaService;
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

    @GetMapping("/filter-cliente/{clienteId}")
    public Page<Fattura> getByCliente(@PathVariable UUID clienteId, Pageable pageable) {
        return fatturaService.findByCliente(clienteId, pageable);
    }

    @GetMapping("/filter-stato")
    public Page<Fattura> getByStato(@RequestParam String stato, Pageable pageable) {
        return fatturaService.findByStato(stato, pageable);
    }

    @GetMapping("/filter-data")
    public Page<Fattura> getByData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data, Pageable pageable) {
        return fatturaService.findByData(data, pageable);
    }

    @GetMapping("/filter-anno")
    public Page<Fattura> getByAnno(@RequestParam int anno, Pageable pageable) {
        return fatturaService.findByAnno(anno, pageable);
    }

    @GetMapping("/filter-importo")
    public Page<Fattura> getByImporto(@RequestParam Double min, @RequestParam Double max, Pageable pageable) {
        return fatturaService.findByRangeImporto(min, max, pageable);
    }
}