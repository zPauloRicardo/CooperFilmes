package me.paulojr.cooperfilme.infra.contexts.script.core;

import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import me.paulojr.cooperfilme.domain.script.core.entity.Script;
import me.paulojr.cooperfilme.domain.script.core.entity.ScriptID;
import me.paulojr.cooperfilme.domain.script.core.gateway.ScriptGateway;
import me.paulojr.cooperfilme.domain.script.core.search.ScriptSearchCommand;
import me.paulojr.cooperfilme.domain.script.customer.entity.CustomerID;
import me.paulojr.cooperfilme.domain.shared.search.Pagination;
import me.paulojr.cooperfilme.infra.contexts.script.core.persistence.ScriptJpaEntity;
import me.paulojr.cooperfilme.infra.contexts.customer.persistence.CustomerJpaEntity;
import me.paulojr.cooperfilme.infra.contexts.script.core.persistence.ScriptJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static me.paulojr.cooperfilme.infra.utils.SpecificationUtils.equal;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class ScriptJpaGateway implements ScriptGateway {

    private final ScriptJpaRepository scriptJpaRepository;

    @Override
    public Optional<Script> findById(ScriptID id) {
        return this.scriptJpaRepository.findById(id.getValue()).map(ScriptJpaEntity::toDomain);
    }

    @Override
    public Script create(Script toSave) {
        return this.scriptJpaRepository.saveAndFlush(ScriptJpaEntity.from(toSave)).toDomain();
    }

    @Override
    public Script update(Script toUpdate) {
        return this.scriptJpaRepository.saveAndFlush(ScriptJpaEntity.from(toUpdate)).toDomain();
    }

    @Override
    public Pagination<Script> findByCustomerId(CustomerID idCustomer, Integer page) {
        final Pageable pageable = PageRequest.of(
                page,
                10,
                Sort.by(Sort.Direction.DESC, "dataCadastro")
        );

        final Page<Script> pageResult = this.scriptJpaRepository.findAllByCliente(new CustomerJpaEntity(idCustomer.getValue()), pageable)
                .map(ScriptJpaEntity::toDomain);

        return new Pagination<>(pageable.getPageNumber(), pageable.getPageSize(), pageResult.getTotalElements(), pageResult.toList());
    }

    @Override
    public Pagination<Script> search(ScriptSearchCommand sq) {

        final Pageable pageable = PageRequest.of(
                sq.page(),
                10,
                Sort.by(Sort.Direction.DESC, "dataCadastro")
        );
        final Specification<ScriptJpaEntity> specification = setupFilters(sq);
        final Page<ScriptJpaEntity> pageResult =
                this.scriptJpaRepository.findAll(Specification.where(specification), pageable);


        return new Pagination<>(sq.page(), pageable.getPageSize(), pageResult.getTotalElements(), pageResult.map(ScriptJpaEntity::toDomain).toList());
    }


    private Specification<ScriptJpaEntity> setupFilters(ScriptSearchCommand aQuery) {
        Specification<ScriptJpaEntity> specifications = null;
        if (aQuery.status() != null) {
            Specification<ScriptJpaEntity> spec = equal("ultimaEtapa", aQuery.status());
            specifications = spec;
        }
        if (aQuery.creationDate() != null) {
            Specification<ScriptJpaEntity> spec = equal("dataCadastro", aQuery.creationDate());
            specifications = specifications == null ? spec : specifications.and(spec);
        }
        if (aQuery.customerEmail() != null) {
            Specification<ScriptJpaEntity> spec = (root, query, criteriaBuilder) -> {
                Join<ScriptJpaEntity, CustomerJpaEntity> clienteJoin = root.join("cliente");
                return criteriaBuilder.equal(clienteJoin.get("email"), aQuery.customerEmail());
            };
            specifications = specifications == null ? spec : specifications.and(spec);
        }
        return specifications;
    }
}
