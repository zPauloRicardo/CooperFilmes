package me.paulojr.cooperfilme.domain.shared.exceptions;

import me.paulojr.cooperfilme.domain.shared.domain.AggregateRoot;
import me.paulojr.cooperfilme.domain.shared.domain.Identifier;
import me.paulojr.cooperfilme.domain.shared.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException {

    protected NotFoundException(final String aMessage, final List<Error> anErrors) {
        super(aMessage, anErrors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final Identifier id
    ) {
        final var anError = "%s com ID %s não foi encontrado.".formatted(
                anAggregate.getSimpleName(),
                id.getValue()
        );
        return new NotFoundException(anError, Collections.emptyList());
    }

    public static NotFoundException with(
            final Class<?> aValueObject,
            final String id
    ) {
        final var anError = "%s com ID %s não foi encontrado.".formatted(
                aValueObject.getSimpleName(),
                id
        );
        return new NotFoundException(anError, Collections.emptyList());
    }

    public static NotFoundException with(final Error error) {
        return new NotFoundException(error.message(), List.of(error));
    }
}
