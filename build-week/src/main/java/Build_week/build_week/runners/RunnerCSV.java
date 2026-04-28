package Build_week.build_week.runners;

import Build_week.build_week.service.ProvinciaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class RunnerCSV implements CommandLineRunner {
    private final ProvinciaService provinciaService;

    public RunnerCSV(ProvinciaService provinciaService) {
        this.provinciaService = provinciaService;
    }

    @Override
    public void run(String... args) throws Exception {
        Path path = Paths.get("CSV/province-italiane.csv");
        provinciaService.saveProvinceIfEmpty(path);
    }
}
