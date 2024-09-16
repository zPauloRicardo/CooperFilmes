package me.paulojr.cooperfilme.domain.shared.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    private ValidationUtils() {
    }

    // Expressão regular para validar email
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    /**
     * Verifica se o número de celular fornecido é válido.
     *
     * <p>Este método remove todos os caracteres que não são números do
     * número de celular fornecido e verifica se o número contém exatamente
     * 11 dígitos, que é o padrão para números de celular.
     *
     * @param phone o número de celular a ser validado.
     * @return {@code true} se o número de celular tiver 11 dígitos, {@code false} caso contrário.
     */
    public static boolean isValidMobilePhoneNumber(String phone) {
        phone = phone.replaceAll("[^0-9]", "");
        return phone.length() == 11;
    }

    /**
     * Verifica se o número de telefone fixo fornecido é válido.
     *
     * <p>Este método remove todos os caracteres que não são números do
     * número de telefone fornecido e verifica se o número contém exatamente
     * 10 dígitos, que é o padrão para números de telefone fixo.
     *
     * @param phone o número de telefone a ser validado.
     * @return {@code true} se o número de telefone tiver 10 dígitos, {@code false} caso contrário.
     */
    public static boolean isValidPhoneNumber(String phone) {
        phone = phone.replaceAll("[^0-9]", "");
        return phone.length() == 10;
    }



    /**
     * Verifica se o endereço de email fornecido é válido de acordo com a expressão regular.
     *
     * @param email o endereço de email a ser validado.
     * @return {@code true} se o endereço de email for válido, {@code false} caso contrário.
     */
    public static boolean isValidEmailAddress(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
