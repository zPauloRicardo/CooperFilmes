package me.paulojr.cooperfilme.infra.utils;

public class SqlUtils {


    public static String like(final String term) {
        if (term == null) return null;
        return "%" + term + "%";
    }
}
