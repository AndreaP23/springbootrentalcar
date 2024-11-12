package com.si2001.webapp.specification;


import com.si2001.webapp.entities.Prenotazione;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.openapitools.model.PrenotazioneDTO;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneSpecification {

    public static Specification<Prenotazione> getPrenotazioneFilter(PrenotazioneDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getUserId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), filter.getUserId()));
            }

            if (filter.getDataInizio() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataInizio"), filter.getDataInizio()));
            }

            if (filter.getDataFine() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataFine"), filter.getDataFine()));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}