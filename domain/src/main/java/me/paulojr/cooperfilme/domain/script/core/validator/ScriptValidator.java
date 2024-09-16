package me.paulojr.cooperfilme.domain.script.core.validator;

import me.paulojr.cooperfilme.domain.script.core.entity.Script;
import me.paulojr.cooperfilme.domain.shared.validation.Error;
import me.paulojr.cooperfilme.domain.shared.validation.ValidationHandler;
import me.paulojr.cooperfilme.domain.shared.validation.Validator;


/**
 * Valida as regras e restrições associadas a um {@link Script}.
 * Herda da classe abstrata {@link Validator}.
 */
public class ScriptValidator extends Validator {

    private final Script script;


    /**
     * Construtor para criar um ScriptValidator.
     *
     * @param aHandler o manipulador de validação que irá receber os erros de validação.
     * @param script o {@link Script} a ser validado.
     */
    public ScriptValidator(ValidationHandler aHandler, Script script) {
        super(aHandler);
        this.script = script;
    }


    /**
     * Executa a validação das regras e restrições do {@link Script}.
     * Chama métodos privados para verificar as restrições específicas do cliente e do texto do roteiro.
     */
    @Override
    public void validate() {
        this.checkCustomerConstraints();
        this.checkTextConstraints();
    }


    /**
     * Verifica as restrições associadas ao cliente do {@link Script}.
     * Adiciona um erro se o cliente do roteiro for nulo.
     */
    private void checkCustomerConstraints() {
        if(this.script.getCustomer() == null) {
            this.appendError(new Error("Cliente não pode ser nulo."));
        }
    }

    /**
     * Verifica as restrições associadas ao texto do {@link Script}.
     * Adiciona erros se o texto for nulo ou vazio.
     */
    private void checkTextConstraints() {
        final String text = this.script.getText();

        if (text == null) this.appendError(new Error("Texto do roteiro não pode ser nulo."));

        if (text != null) {
            if (text.isBlank()) this.appendError(new Error("Texto do roteiro não pode ser vazio."));
        }
    }
}
