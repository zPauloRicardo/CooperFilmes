package me.paulojr.cooperfilme.domain.shared.validation.handler;

import me.paulojr.cooperfilme.domain.shared.exceptions.DomainException;
import me.paulojr.cooperfilme.domain.shared.exceptions.NotificationException;
import me.paulojr.cooperfilme.domain.shared.validation.Error;
import me.paulojr.cooperfilme.domain.shared.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private final List<Error> errors;
    private final String defaultMessage;

    private Notification(final List<Error> errors) {
        this.errors = errors;
        this.defaultMessage = "Não foi possivel realizar a validação.";
    }

    private Notification(final List<Error> errors, String defaultMessage) {
        this.errors = errors;
        this.defaultMessage = defaultMessage;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Throwable t) {
        return create(new Error(t.getMessage()));
    }

    public static Notification create(final String message) {
        return new Notification(new ArrayList<>(), message);
    }

    public static Notification create(final Error anError) {
        return new Notification(new ArrayList<>()).append(anError);
    }

    @Override
    public Notification append(final Error anError) {
        this.errors.add(anError);
        return this;
    }


    public Notification append(final String anError) {
        this.errors.add(new Error(anError));
        return this;
    }

    @Override
    public Notification append(final ValidationHandler anHandler) {
        this.errors.addAll(anHandler.getErrors());
        return this;
    }

    @Override
    public <T> T validate(final Validation<T> aValidation) {
        try {
            return aValidation.validate();
        } catch (final DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (final Throwable t) {
            this.errors.add(new Error(t.getMessage()));
        }
        return null;
    }

    public void throwIfHasError() {
        if (!this.errors.isEmpty()) {
            throw NotificationException.with(defaultMessage, this);
        }
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }

}
