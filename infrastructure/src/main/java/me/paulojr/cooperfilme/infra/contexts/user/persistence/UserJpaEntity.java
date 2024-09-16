package me.paulojr.cooperfilme.infra.contexts.user.persistence;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.paulojr.cooperfilme.domain.user.entity.User;
import me.paulojr.cooperfilme.domain.user.entity.UserID;
import me.paulojr.cooperfilme.domain.user.enums.ProfileEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "CF_USUARIOS")
@Getter
@Setter
@NoArgsConstructor
public class UserJpaEntity implements Serializable {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "perfil", nullable = false, unique = true)
    private String perfil;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "senha")
    private String senha;
    @Column(name = "data_cadastro")
    private Date dataCadastro;

    @Builder
    public UserJpaEntity(UUID id, String nome, String perfil, String email, String senha, Date dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.perfil = perfil;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
    }

    public static UserJpaEntity from(final User user) {
        if (user == null) return null;
        return new UserJpaEntity(
                user.getId().getValue(),
                user.getName(),
                user.getProfile().getKey(),
                user.getEmail(),
                user.getPassword(),
                user.getCreationDate()
        );
    }

    public User toDomain() {
        return User.with(
                new UserID(getId()),
                getNome(),
                ProfileEnum.getFromKey(this.getPerfil()),
                getEmail(),
                getSenha(),
                getDataCadastro()
        );
    }

}
