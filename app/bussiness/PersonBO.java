package bussiness;

import constraints.Validate;
import models.Person;
import models.Person_;
import play.data.validation.ValidationError;

import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by mjovel on 11-15-16.
 */
@Singleton
public class PersonBO extends BasicBO<Person, Long> implements Validate<Person> {

    @Override
    public void validate(Person object, List<ValidationError> errors) {
        CriteriaBuilder cb = jpaApi.em().getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Person> from = query.from(Person.class);
        ArrayList<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(object.id)) {
            predicates.add(cb.equal(from.get(Person_.id), object.id));
        }

        predicates.add(cb.equal(from.get(Person_.name), object.name));

        query.select(cb.count(from))
                .where(predicates.stream().toArray(Predicate[]::new));
        if(jpaApi.em().createQuery(query).getSingleResult() > 0) {
            errors.add(new ValidationError("name", "error.non.unique",
                            Arrays.asList("name")
                    ));
        }
    }
}
