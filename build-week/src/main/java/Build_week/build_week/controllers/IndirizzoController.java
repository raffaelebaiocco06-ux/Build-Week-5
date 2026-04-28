package Build_week.build_week.controllers;

import Build_week.build_week.entities.Indirizzo;
import Build_week.build_week.exceptions.ValidationException;
import Build_week.build_week.payload.IndirizzoDTO;
import Build_week.build_week.service.IndirizzoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/indirizzi")
public class IndirizzoController {
    private final IndirizzoService indirizzoService;

    public IndirizzoController(IndirizzoService indirizzoService) {
        this.indirizzoService = indirizzoService;
    }

    @GetMapping
    public Page<Indirizzo> findAll(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "eventDate") String sortBy) {
        return this.indirizzoService.findAll(page, size, sortBy);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Indirizzo saveEvent(@RequestBody @Validated IndirizzoDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
        return this.indirizzoService.saveIndirizzo(body);
    }

    @GetMapping("/{indirizzoId}")
    public Indirizzo findById(@PathVariable UUID indirizzoId) {
        return this.indirizzoService.findIndirizzoById(indirizzoId);
    }

}
