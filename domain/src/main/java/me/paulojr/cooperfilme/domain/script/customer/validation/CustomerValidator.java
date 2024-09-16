package me.paulojr.cooperfilme.domain.script.customer.validation;

import me.paulojr.cooperfilme.domain.script.customer.entity.Customer;
import me.paulojr.cooperfilme.domain.shared.utils.ValidationUtils;
import me.paulojr.cooperfilme.domain.shared.validation.Error;
import me.paulojr.cooperfilme.domain.shared.validation.ValidationHandler;
import me.paulojr.cooperfilme.domain.shared.validation.Validator;


/**
 * Validador para a entidade {@link Customer}.
 */
public class CustomerValidator extends Validator {



    /** Tamanho máximo permitido para o nome. */
    private static final int NAME_MAX_LENGTH = 255;

    /** Tamanho mínimo permitido para o nome. */
    private static final int NAME_MIN_LENGTH = 3;

    private final Customer customer;


    /**
     * Construtor para {@link CustomerValidator}.
     *
     * @param aHandler o manipulador de validação que irá receber erros.
     * @param customer o cliente a ser validado.
     */
    public CustomerValidator(ValidationHandler aHandler, Customer customer) {
        super(aHandler);
        this.customer = customer;
    }


    /**
     * Realiza a validação das restrições do cliente.
     * <p>
     * Este método verifica as restrições de nome e e-mail do cliente.
     *
     */
    @Override
    public void validate() {
        this.checkNameConstraints();
        this.checkEmailConstraints();
    }


    /**
     * Verifica as restrições do nome do cliente.
     * <p>
     * O nome não pode ser nulo, vazio, ou ter um comprimento fora do intervalo definido.
     */
    private void checkNameConstraints() {
        final String nome = this.customer.getName();

        if (nome == null) this.appendError(new Error("Nome não pode ser nulo."));

        if (nome != null) {

            if (nome.isBlank()) this.appendError(new Error("Nome não pode ser vazio."));

            final int length = nome.trim().length();
            if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH)
                this.appendError(new Error(String.format("Nome deve ter entre %s e %s caracteres.", NAME_MIN_LENGTH, NAME_MAX_LENGTH)));

        }
    }

    /**
     * Verifica as restrições do e-mail do cliente.
     * <p>
     * O e-mail não pode ser nulo, vazio, ou inválido.
     */
    private void checkEmailConstraints() {
        final String email = this.customer.getEmail();

        if (email == null) this.appendError(new Error("Email não pode ser nulo."));

        if (email != null) {

            if (email.isBlank()) this.appendError(new Error("Email não pode ser vazio."));

            if (!ValidationUtils.isValidEmailAddress(email)) this.appendError(new Error("Email inválido."));
        }
    }


    /**
     * Verifica as restrições do telefone do cliente.
     * <p>
     * O telefonel não pode ser nulo, vazio, ou inválido.
     */
    private void checkPhoneConstraints() {
        final String phone = this.customer.getPhone();

        if (phone == null) this.appendError(new Error("Telefone não pode ser nulo."));

        if (phone != null) {

            if (phone.isBlank()) this.appendError(new Error("Telefone não pode ser vazio."));

            if (!ValidationUtils.isValidPhoneNumber(phone) && !ValidationUtils.isValidMobilePhoneNumber(phone)) this.appendError(new Error("Telefone inválido."));
        }
    }
}
