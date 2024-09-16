package me.paulojr.cooperfilme.infra.contexts.script.vote.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.paulojr.cooperfilme.domain.script.core.entity.Script;
import me.paulojr.cooperfilme.domain.script.core.enums.VoteTypeEnum;
import me.paulojr.cooperfilme.domain.script.core.vo.VoteVo;
import me.paulojr.cooperfilme.domain.user.entity.UserID;
import me.paulojr.cooperfilme.infra.contexts.script.core.persistence.ScriptJpaEntity;

import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "cf_voto_roteiros")
@NoArgsConstructor
@Getter
public class VoteJpaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_roteiro")
    private ScriptJpaEntity roteiro;

    @Column(name = "data_voto")
    private Date dataVoto;

    @Column(name = "tipo_voto")
    private String tipoVoto;

    @Column(name = "id_usuario")
    private UUID idUsuario;

    @Column(name = "nome_usuario")
    private String nomeUsuario;

    public VoteJpaEntity(UUID id, ScriptJpaEntity scriptJpa, Date dataVoto, String tipoVoto, UUID idUsuario, String nomeUsuario) {
        this.id = id;
        this.roteiro = scriptJpa;
        this.dataVoto = dataVoto;
        this.tipoVoto = tipoVoto;
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
    }

    public static VoteJpaEntity from(VoteVo voteVo, ScriptJpaEntity script) {
        return new VoteJpaEntity(
                voteVo.id() == null ? UUID.randomUUID() : voteVo.id(),
                script,
                voteVo.voteDate(),
                voteVo.type().getKey(),
                voteVo.userID() == null ? null : voteVo.userID().getValue(),
                voteVo.userName());
    }


    public VoteVo toDomain() {
        return new VoteVo(
                this.id,
                this.idUsuario != null ? UserID.from(this.idUsuario) : null,
                this.nomeUsuario,
                VoteTypeEnum.getByKey(this.tipoVoto),
                this.dataVoto
        );
    }
}
