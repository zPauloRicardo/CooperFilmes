package me.paulojr.cooperfilme.domain.user.entity;

import me.paulojr.cooperfilme.domain.user.enums.ProfileEnum;
import me.paulojr.cooperfilme.domain.shared.domain.AggregateRoot;
import me.paulojr.cooperfilme.domain.shared.utils.InstantUtils;
import me.paulojr.cooperfilme.domain.shared.validation.ValidationHandler;
import lombok.Getter;

import java.util.Date;

@Getter
public class User extends AggregateRoot<UserID> {

    private final String name;
    private final String email;
    private final ProfileEnum profile;
    private final String password;
    private final Date creationDate;


    protected User(UserID id, String name, String email, ProfileEnum profile, String password, Date creationDate) {
        super(id);
        this.name = name;
        this.profile = profile;
        this.email = handleEmail(email);
        this.password = password;
        this.creationDate = creationDate;
    }

    protected User(final User user) {
        super(user.getId());
        this.name = user.getName();
        this.profile = user.getProfile();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.creationDate = user.getCreationDate();
    }

    public static User with(
            final UserID id,
            final String nome,
            final ProfileEnum profile,
            final String email,
            final String senha,
            final Date dataCadastro

    ) {
        return new User(
                id,
                nome,
                email,
                profile,
                senha,
                dataCadastro);
    }


    public boolean isAnalist() {
        return this.profile == ProfileEnum.ANALISTA;
    }

    public boolean isReviewer() {
        return this.profile == ProfileEnum.REVISOR;
    }

    public boolean isApprover() {
        return this.profile == ProfileEnum.APROVADOR;
    }

    private static String handleEmail(String email) {
        return email.toLowerCase();
    }

    @Override
    public void validate(final ValidationHandler handler) {

    }
}
