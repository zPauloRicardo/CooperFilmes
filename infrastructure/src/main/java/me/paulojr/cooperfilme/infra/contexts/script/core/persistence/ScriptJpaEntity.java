package me.paulojr.cooperfilme.infra.contexts.script.core.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.paulojr.cooperfilme.domain.script.core.entity.Script;
import me.paulojr.cooperfilme.domain.script.core.entity.ScriptID;
import me.paulojr.cooperfilme.infra.contexts.customer.persistence.CustomerJpaEntity;
import me.paulojr.cooperfilme.infra.contexts.script.step.persistence.StepJpaEntity;
import me.paulojr.cooperfilme.infra.contexts.script.vote.persistence.VoteJpaEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cf_roteiros")
@NoArgsConstructor
@Getter
public class ScriptJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;


    @Column(name = "texto", nullable = false)
    private String texto;

    @JoinColumn(name = "id_cliente", nullable = false)
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER, targetEntity = CustomerJpaEntity.class)
    @Setter
    private CustomerJpaEntity cliente;


    @OneToMany(mappedBy = "roteiro", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Setter
    private List<StepJpaEntity> etapas = new ArrayList<>();

    @OneToMany(mappedBy = "roteiro", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Setter
    private List<VoteJpaEntity> votos = new ArrayList<>();

    @Column(name = "data_cadastro")
    private Date dataCadastro;

    @Column(name = "ultima_etapa")
    private Integer ultimaEtapa;

    public ScriptJpaEntity(UUID id) {
        this.id = id;
    }

    public ScriptJpaEntity(UUID id, String texto, CustomerJpaEntity cliente, List<StepJpaEntity> etapas, List<VoteJpaEntity> votos, Date dataCadastro, Integer ultimaEtapa) {
        this.id = id;
        this.texto = texto;
        this.cliente = cliente;
        this.etapas = etapas;
        this.votos = votos;
        this.dataCadastro = dataCadastro;
        this.ultimaEtapa = ultimaEtapa;
    }

    public static ScriptJpaEntity from(Script script) {
        final var scriptJpaEntity = new ScriptJpaEntity(
                script.getId() != null ? script.getId().getValue() : null,
                script.getText(),
                CustomerJpaEntity.from(script.getCustomer()),
                new ArrayList<>(),
                new ArrayList<>(),
                script.getCreationDate(),
                script.getLastStep().type().getKey()
        );
        scriptJpaEntity.setEtapas(script.getSteps().stream().map(i -> StepJpaEntity.from(i, scriptJpaEntity)).toList());
        scriptJpaEntity.setVotos(script.getVotes().stream().map(i -> VoteJpaEntity.from(i, scriptJpaEntity)).toList());
        return scriptJpaEntity;
    }

    public Script toDomain() {
        return Script.with(ScriptID.from(this.id.toString()), this.cliente.toDomain(), this.texto, this.etapas.stream().map(StepJpaEntity::toDomain).toList(), this.votos.stream().map(VoteJpaEntity::toDomain).toList(), this.dataCadastro);
    }
}
