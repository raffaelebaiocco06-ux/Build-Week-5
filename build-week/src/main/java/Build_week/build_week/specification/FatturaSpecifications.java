package Build_week.build_week.specification;

import Build_week.build_week.entities.Fattura;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

public class FatturaSpecifications {

    public static Specification<Fattura> hasCliente(UUID clienteId) {
        return (root, query, cb) ->
                clienteId == null ? null : cb.equal(root.get("cliente").get("id"), clienteId);
    }

    public static Specification<Fattura> hasStato(String nomeStato) {
        return (root, query, cb) -> {
            if (nomeStato == null) return null;
            return cb.equal(cb.lower(root.get("stato").get("nome")), nomeStato.toLowerCase());
        };
    }

    public static Specification<Fattura> hasData(LocalDate data) {
        return (root, query, cb) ->
                data == null ? null : cb.equal(root.get("data"), data);
    }

    public static Specification<Fattura> hasAnno(Integer anno) {
        return (root, query, cb) -> {
            if (anno == null) return null;
            LocalDate inizioAnno = LocalDate.of(anno, 1, 1);
            LocalDate fineAnno = LocalDate.of(anno, 12, 31);
            return cb.between(root.get("data"), inizioAnno, fineAnno);
        };
    }

    public static Specification<Fattura> hasImportoBetween(Double min, Double max) {
        return (root, query, cb) -> {
            if (min != null && max != null) return cb.between(root.get("importo"), min, max);
            if (min != null) return cb.greaterThanOrEqualTo(root.get("importo"), min);
            if (max != null) return cb.lessThanOrEqualTo(root.get("importo"), max);
            return null;
        };
    }
}