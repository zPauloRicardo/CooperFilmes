package me.paulojr.cooperfilme.domain.shared.exceptions;

import me.paulojr.cooperfilme.domain.shared.validation.handler.Notification;

public class NotificationException extends DomainException {

    public NotificationException(final String aMessage, final Notification notification) {
        super(aMessage, notification.getErrors());
    }

    public static NotificationException with(final String aMessage, final Notification notification) {
        return new NotificationException(aMessage, notification);
    }
}
