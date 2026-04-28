package Build_week.build_week.runners;

import Build_week.build_week.service.ComuneService;
import Build_week.build_week.service.ProvinciaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class RunnerCSV implements CommandLineRunner {
    private final ProvinciaService provinciaService;
    private final ComuneService comuneService;

    public RunnerCSV(ProvinciaService provinciaService, ComuneService comuneService) {
        this.provinciaService = provinciaService;
        this.comuneService = comuneService;
    }

    @Override
    public void run(String... args) throws Exception {
        Path pathProvince = Paths.get("CSV/province-italiane.csv");
        provinciaService.saveProvinceIfEmpty(pathProvince);

        Path pathComuni = Paths.get("CSV/comuni-italiani.csv");
        comuneService.saveComuniIfEmpty(pathComuni);
    }
}
