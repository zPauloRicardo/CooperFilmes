package me.paulojr.cooperfilme.infra.contexts.customer.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerJpaEntity, UUID> {

    boolean existsById(UUID id);

    void deleteById(UUID id);

    Optional<CustomerJpaEntity> findByEmail(String email);
}
