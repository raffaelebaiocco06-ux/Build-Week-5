package Build_week.build_week.service;

import Build_week.build_week.entities.Comune;
import Build_week.build_week.entities.Provincia;
import Build_week.build_week.exceptions.NotFoundException;
import Build_week.build_week.repository.ComuneRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ComuneService {

    private final ComuneRepository comuneRepository;
    private final ProvinciaService provinciaService;

    public ComuneService(ComuneRepository comuneRepository, ProvinciaService provinciaService) {
        this.comuneRepository = comuneRepository;
        this.provinciaService = provinciaService;
    }


    public List<Comune> readComune(Path path) throws Exception {

        List<Comune> comuni = new ArrayList<>();

        Map<String, Provincia> provinciaMap = provinciaService.findAll()
                .stream()
                .collect(Collectors.toMap(provincia -> provincia.getNome().toLowerCase().trim(),
                        provincia -> provincia));

        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(path.toFile()))
                .withCSVParser(parser)
                .build()) {

            String[] riga;

            while ((riga = reader.readNext()) != null) {
                if (riga.length < 4) continue;

                String comune = riga[2];
                String provincia = riga[3].toLowerCase().trim();

                Provincia provincia1 = provinciaMap.get(provincia);
                Comune comune1 = new Comune(comune, provincia1);

                comuni.add(comune1);
            }
            return comuni;
        }
    }

    public void saveComuniIfEmpty(Path path) throws Exception {
        if (comuneRepository.count() == 0) {
            List<Comune> comuni = readComune(path);
            for (Comune c : comuni) {
                comuneRepository.save(c);
            }
        }
    }

    public Comune findComuneById(UUID comuneId) {
        return this.comuneRepository.findById(comuneId).orElseThrow(() -> new NotFoundException(comuneId));
    }
}
