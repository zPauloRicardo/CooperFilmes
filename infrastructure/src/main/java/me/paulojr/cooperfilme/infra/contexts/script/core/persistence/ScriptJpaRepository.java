package me.paulojr.cooperfilme.infra.contexts.script.core.persistence;

import me.paulojr.cooperfilme.infra.contexts.customer.persistence.CustomerJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScriptJpaRepository extends JpaRepository<ScriptJpaEntity, UUID> {

    Page<ScriptJpaEntity> findAll(Specification<ScriptJpaEntity> spec, Pageable page);
    Page<ScriptJpaEntity> findAllByCliente(CustomerJpaEntity cliente, Pageable pageable);
}
