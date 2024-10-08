package me.paulojr.cooperfilme.infra.utils;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class SpecificationUtils {

    private SpecificationUtils() {
    }

    public static <T> Specification<T> like(final String prop, final String term) {
        return (root, query, cb) -> cb.like(cb.upper(root.get(prop).as(String.class)), SqlUtils.like(term.toUpperCase()));
    }

    public static <T> Specification<T> equal(final String prop, final String term) {
        return (root, query, cb) -> cb.equal(root.get(prop).as(String.class), term);
    }

    public static <T> Specification<T> equal(final String prop, final Integer term) {
        return (root, query, cb) -> cb.equal(root.get(prop), term);
    }

    public static <T> Specification<T> equal(final String prop, final Date term) {
        return (root, query, cb) -> cb.equal(root.get(prop), term);
    }

    public static <T> Specification<T> like(final String prop, final String term, final String joinProp) {
        return (root, query, cb) -> {
            var table = root.join(prop);
            return cb.like(cb.upper(table.get(joinProp).as(String.class)), SqlUtils.like(term.toUpperCase()));
        };
    }

    public static <T> Specification<T> equal(final String prop, final String term, final String joinProp) {
        return (root, query, cb) -> {
            var table = root.join(prop);
            return cb.equal(table.get(joinProp).as(String.class), term);
        };
    }


    public static <T> Specification<T> equalDate(String field, Date dateTime) {
        return (root, query, cb) -> {
            LocalDate localDate = dateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Date startDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
            return cb.between(root.get(field), startDate, endDate);
        };
    }

}