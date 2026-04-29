package Build_week.build_week.specification;

import Build_week.build_week.entities.Cliente;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ClienteSpecification {


    public static Specification<Cliente> nomeContatto(String nome){
        return(root, query, criteriaBuilder) -> {
            if(nome==null || nome.isBlank()) return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("nomeContatto")), "%" + nome.toLowerCase() + "%");
        };
    }
    public static Specification<Cliente> FatturatoBeetwen(Long min,Long max){
        return (root, query, criteriaBuilder) -> {
            if (min == null && max == null) return null;

            if (min != null && max != null) {
                return criteriaBuilder.between(root.get("fatturatoTot"), min, max);
            }

            if (min != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("fatturatoTot"), min);
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("fatturatoTot"), max);
        };
    }
    public static Specification<Cliente> dataInserimento(LocalDate data){
        return ((root, query, criteriaBuilder) -> {
            if(data ==null) return null;
            return criteriaBuilder.equal(root.get("dataInserimento"),data);
        });
    }

    public static Specification<Cliente> dataUltimoContatto(LocalDate data){
        return ((root, query, criteriaBuilder) -> {
            if(data ==null) return null;
            return criteriaBuilder.equal(root.get("dataUltimoContatto"),data);
        });
    }


}
