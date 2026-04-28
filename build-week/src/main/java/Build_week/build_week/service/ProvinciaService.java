package Build_week.build_week.service;

import Build_week.build_week.entities.Provincia;
import Build_week.build_week.exceptions.NotFoundException;
import Build_week.build_week.repository.ProvinciaRepository;
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

@Service
@Slf4j
public class ProvinciaService {
    private final ProvinciaRepository provinciaRepository;

    public ProvinciaService(ProvinciaRepository provinciaRepository) {
        this.provinciaRepository = provinciaRepository;
    }

    public List<Provincia> readProvince(Path path) throws Exception {

        List<Provincia> province = new ArrayList<>();

        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(path.toFile()))
                .withCSVParser(parser)
                .build()) {
            String[] riga;
            while ((riga = reader.readNext()) != null) {

                if (riga.length < 3) continue;

                String sigla = riga[0];
                String provincia = riga[1];
                String regione = riga[2];

                Provincia provincia1 = new Provincia(provincia, sigla, regione);
                province.add(provincia1);
            }
            return province;
        }
    }

    public void saveProvinceIfEmpty(Path path) throws Exception {
        if (provinciaRepository.count() < 100) {
            List<Provincia> province = readProvince(path);
            for (Provincia p : province) {
                provinciaRepository.save(p);
            }
        }
    }

    public Provincia findByNome(String nome) {
        return this.provinciaRepository.findByNome(nome).orElseThrow(() -> new NotFoundException("La provincia " + nome + " non è stata trovata"));
    }

    public List<Provincia> findAll() {
        return this.provinciaRepository.findAll();
    }
}
