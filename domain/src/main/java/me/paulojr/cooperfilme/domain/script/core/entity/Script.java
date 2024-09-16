package me.paulojr.cooperfilme.domain.script.core.entity;

import lombok.Getter;
import me.paulojr.cooperfilme.domain.script.core.enums.StepTypeEnum;
import me.paulojr.cooperfilme.domain.script.core.enums.VoteTypeEnum;
import me.paulojr.cooperfilme.domain.script.core.validator.ScriptValidator;
import me.paulojr.cooperfilme.domain.script.core.vo.StepVo;
import me.paulojr.cooperfilme.domain.script.core.vo.VoteVo;
import me.paulojr.cooperfilme.domain.script.customer.entity.Customer;
import me.paulojr.cooperfilme.domain.shared.domain.AggregateRoot;
import me.paulojr.cooperfilme.domain.shared.exceptions.DomainException;
import me.paulojr.cooperfilme.domain.shared.exceptions.NoStacktraceException;
import me.paulojr.cooperfilme.domain.shared.utils.InstantUtils;
import me.paulojr.cooperfilme.domain.shared.validation.Error;
import me.paulojr.cooperfilme.domain.shared.validation.ValidationHandler;
import me.paulojr.cooperfilme.domain.user.entity.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 * Classe que representa um roteiro (Script).
 *
 * <p>Um ‘Script’ é associado a um cliente (Customer), possui um texto descritivo, uma lista de etapas
 * ({@link StepVo}) que documentam o seu progresso, e uma lista de votos ({@link VoteVo}) que
 * controlam a sua aprovação ou rejeição.
 */
@Getter
public class Script extends AggregateRoot<ScriptID> {

    private final Customer customer;
    private final String text;
    private final List<StepVo> steps;
    private final List<VoteVo> votes;
    private final Date creationDate;

    /**
     * Construtor protegido da classe Script. Cria uma instância de Script com os atributos fornecidos.
     *
     * @param scriptID     o identificador único do Script.
     * @param customer     o cliente associado ao Script.
     * @param text         o texto descritivo do Script.
     * @param steps        as etapas associadas ao Script.
     * @param votes        os votos associados ao Script.
     * @param creationDate data de criação do script.
     */
    protected Script(ScriptID scriptID, Customer customer, String text, List<StepVo> steps, List<VoteVo> votes, Date creationDate) {
        super(scriptID);
        this.customer = customer;
        this.text = text;
        this.steps = new ArrayList<>(steps);
        this.votes = new ArrayList<>(votes);
        this.creationDate = creationDate;
    }


    /**
     * Cria um Script em estado "Aguardando Análise".
     *
     * @param customer o cliente associado ao Script.
     * @param text     o texto descritivo do Script.
     * @return uma nova instância de {@link Script}.
     */
    public static Script newScript(Customer customer, String text) {
        return new Script(null,
                customer,
                text,
                List.of(new StepVo(null, null, StepTypeEnum.AGUARDANDO_ANALISE, null)),
                new ArrayList<>(),
                Date.from(InstantUtils.now()));


    }

    /**
     * Cria um Script com os atributos fornecidos.
     *
     * @param id           o identificador único do Script.
     * @param customer     o cliente associado ao Script.
     * @param text         o texto descritivo do Script.
     * @param steps        as etapas associadas ao Script.
     * @param votes        os votos associados ao Script.
     * @param creationDate data de criação do script.
     * @return uma nova instância de {@link Script}.
     */
    public static Script with(ScriptID id, Customer customer, String text, List<StepVo> steps, List<VoteVo> votes, Date creationDate) {
        return new Script(id, customer, text, steps, votes, creationDate);

    }


    /**
     * Método para um analista assumir a análise de um Script.
     *
     * @param user o usuário que está assumindo a análise.
     * @throws DomainException se o usuário não for um analista ou se o Script já estiver finalizado.
     */
    public void assumeAnalysis(User user) {
        if (!user.isAnalist()) {
            throw DomainException.with(new Error("Apenas analsitas podem assumir analise."));
        }
        this.checkIfFinished();
        final StepTypeEnum emAnalise = StepTypeEnum.EM_ANALISE;
        this.checkIfCanBeMovedTo(emAnalise);
        this.registerStep(user, null, emAnalise);
    }

    /**
     * Método para um usuário rejeitar o Script com uma justificativa.
     *
     * @param user          o usuário que está rejeitando o Script.
     * @param justification a justificativa da rejeição.
     * @throws DomainException se o usuário for um revisor ou se a etapa atual não permitir rejeição.
     */
    public void reject(User user, String justification) {
        if (user.isReviewer()) {
            throw DomainException.with(new Error("Revisores não podem recusar roteiros."));
        }
        checkJustification(justification);
        final StepTypeEnum recusado = StepTypeEnum.RECUSADO;
        if (!this.getLastStep().type().getNextSteps().contains(recusado)) {
            throw DomainException.with(new Error("Etapa não permite recusar."));
        }
        this.registerStep(user, justification, recusado);
    }


    /**
     * Envia o Script para a etapa de revisão, alterando o status para "Aguardando Revisão".
     * Apenas usuários com permissão de analista podem realizar essa ação.
     *
     * @param user          o usuário que está enviando o Script para revisão.
     * @param justification a justificativa para enviar o Script para revisão.
     * @throws DomainException se o usuário não for um analista, se o Script já tiver sido finalizado,
     *                         ou se não puder ser movido para a etapa de revisão.
     */
    public void sendToReview(User user, String justification) {
        if (!user.isAnalist()) {
            throw DomainException.with(new Error("Apenas analsitas podem enviar para revisão."));
        }
        checkJustification(justification);
        this.checkIfFinished();
        final StepTypeEnum aguardandoRevisao = StepTypeEnum.AGUARDANDO_REVISAO;
        this.checkIfCanBeMovedTo(aguardandoRevisao);
        this.registerStep(user, justification, aguardandoRevisao);
    }


    /**
     * Permite que um revisor assuma a revisão do Script, alterando o status para "Em Revisão".
     * Apenas revisores têm permissão para assumir essa tarefa.
     *
     * @param user o revisor que está assumindo a revisão do Script.
     * @throws DomainException se o usuário não for um revisor, se o Script já tiver sido finalizado,
     *                         ou se o Script não puder ser movido para a etapa de revisão.
     */
    public void assumeReview(User user) {
        if (!user.isReviewer()) {
            throw DomainException.with(new Error("Apenas revisores podem assumir revisão."));
        }
        this.checkIfFinished();
        final StepTypeEnum emRevisao = StepTypeEnum.EM_REVISAO;
        this.checkIfCanBeMovedTo(emRevisao);
        this.registerStep(user, null, emRevisao);
    }

    /**
     * Envia o Script para a etapa de aprovação, alterando o status para "Aguardando Aprovação".
     * Apenas revisores podem realizar essa ação.
     *
     * @param user          o revisor que está enviando o Script para aprovação.
     * @param justification a justificativa para enviar o Script para aprovação.
     * @throws DomainException se o usuário não for um revisor, se o Script já tiver sido finalizado,
     *                         ou se não puder ser movido para a etapa de aprovação.
     */
    public void sendToApprove(User user, String justification) {
        if (!user.isReviewer()) {
            throw DomainException.with(new Error("Apenas revisores podem enviar para aprovação."));
        }
        checkJustification(justification);
        this.checkIfFinished();
        final StepTypeEnum aguardandoRevisao = StepTypeEnum.AGUARDANDO_APROVACAO;
        this.checkIfCanBeMovedTo(aguardandoRevisao);
        this.registerStep(user, justification, aguardandoRevisao);
    }


    /**
     * Aprova o Script, alterando o status para "Aprovado".
     * O método registra a aprovação e verifica se a etapa atual permite essa ação.
     *
     * @param user o usuário que está realizando a aprovação.
     * @throws DomainException se a etapa atual não permitir a aprovação,
     *                         ou se o Script já tiver sido finalizado.
     */
    private void approve(User user) {
        final StepTypeEnum aprovado = StepTypeEnum.APROVADO;
        if (!this.getLastStep().type().getNextSteps().contains(aprovado)) {
            throw DomainException.with(new Error("Etapa não permite aprovar."));
        }
        this.registerStep(user, "Aprovado por votação.", aprovado);
    }


    /**
     * Registra o voto de um usuário no Script e atualiza o status do Script com base nos votos.
     * Apenas usuários que têm permissão para aprovação podem votar.
     * O voto pode ser de aprovação ou reprovação.
     * O comportamento para cada tipo de voto é o seguinte:
     * - **Aprovado**: Se houver três ou mais votos de aprovação, o Script será aprovado.
     * - **Reprovado**: O Script será rejeitado automaticamente com base no voto.
     *
     * @param user     o usuário que está realizando o voto.
     * @param voteType o tipo de voto ({@link VoteTypeEnum}).
     * @throws DomainException se o usuário não tiver permissão para votar, se a etapa atual não permitir votos,
     *                         ou se ocorrer um erro ao processar o voto.
     */
    public void vote(User user, VoteTypeEnum voteType) {
        if (!user.isApprover()) {
            throw DomainException.with(new Error("Apenas aprovadores podem votar."));
        }

        this.checkIfFinished();

        // Verifica se a etapa permite votos
        if (!this.getLastStep().type().isPermitVote()) {
            throw DomainException.with(new Error("Etapa não permite votos."));
        }


        if(this.votes.stream().anyMatch(v -> v.userID().equals(user.getId()))){
            throw DomainException.with(new Error("Só é permitido um voto por usuário."));
        }

        this.registerVote(user, voteType);

        // Verifica se o voto é de reprovado e passa o status para o mesmo,
        // se for aprovado verifica se tem mais votos aprovados e altera o status para aprovado,
        // se for erro lança o erro.
        switch (voteType) {
            case APROVADO -> {
                if (this.votes.stream().filter(voteVo -> voteVo.type().equals(VoteTypeEnum.APROVADO)).count() >= 3) {
                    this.approve(user);
                }
            }
            case REPROVADO -> this.reject(user, "Rejeitado por votação.");
            case ERRO -> throw DomainException.with(new Error(voteType.getDescription()));
        }
    }


    /**
     * Verifica se o Script já foi finalizado.
     * Caso a última etapa seja uma etapa final, lança uma exceção indicando que o Script já foi concluído.
     *
     * @throws DomainException se o Script estiver em uma etapa final.
     */
    private void checkIfFinished() {
        // Obtem ultima etapa lançada
        final StepTypeEnum lastStep = this.getLastStep().type();

        // Verifica se é etapa final ou ainda permite alteração
        if (lastStep.isFinalStatus()) {
            throw DomainException.with(new Error(
                    String.format("Este roteiro já foi %s.", lastStep.getDescription().toLowerCase())
            ));
        }
    }


    /**
     * Verifica se a etapa atual permite mover para a próxima etapa indicada.
     * Caso a transição não seja permitida, lança uma exceção.
     *
     * @param stepType a próxima etapa para a qual deseja-se mover.
     * @throws DomainException se a etapa atual não permitir a transição para a etapa fornecida.
     */
    private void checkIfCanBeMovedTo(StepTypeEnum stepType) {
        // Obtem a ultima etapa lançada e verifica se pode mover para a proxima indicada.
        if (!this.getLastStep().type().getNextSteps().contains(stepType)) {
            throw DomainException.with(new Error(
                    String.format("Etapa não permite enviar mover para %s.", stepType.getDescription().toLowerCase())
            ));
        }
    }

    /**
     * Valida a justificativa fornecida. Lança uma exceção se a justificativa for nula ou vazia.
     *
     * @param justification a justificativa a ser validada.
     * @throws DomainException se a justificativa for nula ou vazia.
     */
    private static void checkJustification(String justification) {
        if (justification == null || justification.isBlank()) {
            throw DomainException.with(new Error("Justificativa obrigatoria."));
        }
    }

    /**
     * Retorna a última etapa registrada no Script com base na data de registro da etapa.
     *
     * @return a última etapa registrada ({@link StepVo}).
     * @throws NoStacktraceException se não houver etapas cadastradas para o Script.
     */
    public StepVo getLastStep() {
        return steps.stream()
                .max(Comparator.comparing(StepVo::stepDate))
                .orElseThrow(() -> new NoStacktraceException("Não há etapa cadastrada para este roteiro."));
    }


    /**
     * Registra uma nova etapa no Script associada a um usuário, justificativa e tipo de etapa.
     *
     * @param user          o usuário que está registrando a etapa.
     * @param justification a justificativa da etapa.
     * @param stepTypeEnum  o tipo da nova etapa.
     */
    private void registerStep(User user, String justification, StepTypeEnum stepTypeEnum) {
        this.steps.add(new StepVo(user.getId(), user.getName(), stepTypeEnum, justification));
    }


    /**
     * Registra um novo voto no Script associado a um usuário e tipo de voto.
     *
     * @param user         o usuário que está registrando o voto.
     * @param voteTypeEnum o tipo do voto ({@link VoteTypeEnum}).
     */
    private void registerVote(User user, VoteTypeEnum voteTypeEnum) {
        this.votes.add(new VoteVo(user.getId(), user.getName(), voteTypeEnum));
        if(!this.getLastStep().type().equals(StepTypeEnum.EM_APROVACAO))
            this.registerStep(user, null, StepTypeEnum.EM_APROVACAO);
    }

    /**
     * Valida o Script com base nas regras de negócio aplicáveis.
     *
     * @param handler o validador que processa as regras de validação.
     */
    @Override
    public void validate(ValidationHandler handler) {
        new ScriptValidator(handler, this).validate();
    }
}
