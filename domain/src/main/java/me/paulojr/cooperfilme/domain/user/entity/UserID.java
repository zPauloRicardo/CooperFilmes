package me.paulojr.cooperfilme.domain.user.entity;

import me.paulojr.cooperfilme.domain.shared.domain.Identifier;
import me.paulojr.cooperfilme.domain.shared.utils.UUIDUtils;

import java.util.Objects;
import java.util.UUID;

public class UserID extends Identifier<UUID> {

    private final UUID uuid;

    public UserID(UUID uuid) {
        this.uuid = uuid;
    }

    public static UserID unique() {
        return UserID.from(UUIDUtils.uuid());
    }

    public static UserID from(final String anId) {
        return new UserID(UUIDUtils.getFromString(anId));
    }

    public static UserID from(final UUID anId) {
        return new UserID(anId);
    }

    @Override
    public UUID getValue() {
        return this.uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserID userID = (UserID) o;
        return Objects.equals(uuid, userID.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
