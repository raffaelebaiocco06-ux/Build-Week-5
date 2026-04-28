package Build_week.build_week.service;

import Build_week.build_week.repository.ComuneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ComuneService {

    private final ComuneRepository comuneRepository;
    private final ProvinciaService provinciaService;

    public ComuneService(ComuneRepository comuneRepository, ProvinciaService provinciaService) {
        this.comuneRepository = comuneRepository;
        this.provinciaService = provinciaService;
    }

//    public List<Comune> readComune(Path path) throws Exception {
//
//        List<Comune> province = new ArrayList<>();
//
//        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
//
//        try (CSVReader reader = new CSVReaderBuilder(new FileReader(path.toFile()))
//                .withCSVParser(parser)
//                .build()) {
//            String[] riga;
//            while ((riga = reader.readNext()) != null) {
//
//                if (riga.length < 3) continue;
//
//                String comune = riga[3];
//                String provincia = riga[4];
//
//                Comune comune1 = new Comune(comune, provincia);
//            }
//            return province;
//        }
//    }
//
//    public void saveProvinceIfEmpty(Path path) throws Exception {
//        if (provinciaRepository.count() < 100) {
//            List<Provincia> province = readProvince(path);
//            for (Provincia p : province) {
//                provinciaRepository.save(p);
//            }
//        }
//    }
}
