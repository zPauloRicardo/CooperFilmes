package me.paulojr.cooperfilme.infra.contexts.script.step.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import me.paulojr.cooperfilme.domain.script.core.entity.Script;
import me.paulojr.cooperfilme.domain.script.core.enums.StepTypeEnum;
import me.paulojr.cooperfilme.domain.script.core.vo.StepVo;
import me.paulojr.cooperfilme.domain.user.entity.UserID;
import me.paulojr.cooperfilme.infra.contexts.script.core.persistence.ScriptJpaEntity;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "cf_etapa_roteiros")
@Getter
public class StepJpaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_roteiro")
    private ScriptJpaEntity roteiro;

    @Column(name = "data_etapa")
    private Date dataEtapa;

    @Column(name = "tipo_etapa")
    private Integer tipoEtapa;

    @Column(name = "justificativa")
    private String justificativa;

    @Column(name = "id_usuario")
    private UUID idUsuario;

    @Column(name = "nome_usuario")
    private String nomeUsuario;

    public StepJpaEntity() {
    }

    public StepJpaEntity(UUID id, ScriptJpaEntity scriptJpaEntity, Date dataEtapa, Integer tipoEtapa, String justificativa, UUID idUsuario, String nomeUsuario) {
        this.id = id;
        this.roteiro = scriptJpaEntity;
        this.dataEtapa = dataEtapa;
        this.tipoEtapa = tipoEtapa;
        this.justificativa = justificativa;
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
    }

    public static StepJpaEntity from(StepVo stepVo, ScriptJpaEntity script) {
        return new StepJpaEntity(
                stepVo.id() == null ? UUID.randomUUID() : stepVo.id(),
                script,
                stepVo.stepDate(),
                stepVo.type().getKey(),
                stepVo.justification(),
                stepVo.userId() == null ? null : stepVo.userId().getValue(),
                stepVo.userName());
    }


    public StepVo toDomain() {
        return new StepVo(
                this.id,
                this.idUsuario != null ? UserID.from(this.idUsuario) : null,
                this.nomeUsuario,
                this.dataEtapa,
                StepTypeEnum.getByKey(this.tipoEtapa),
                this.justificativa
        );
    }
}
