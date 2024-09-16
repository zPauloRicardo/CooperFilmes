package me.paulojr.cooperfilme.domain.user.gateway;

import me.paulojr.cooperfilme.domain.user.entity.User;
import me.paulojr.cooperfilme.domain.user.entity.UserID;

import java.util.Optional;

public interface UserGateway {

    /**
     * Busca um usuário pelo seu identificador único.
     *
     * <p>Este método recebe um identificador de usuário e retorna um
     * {@link Optional} contendo o usuário correspondente, se encontrado.
     *
     * @param anUserID o identificador único do usuário.
     * @return um {@link Optional} contendo o usuário, se encontrado; caso contrário, um Optional vazio.
     */
    Optional<User> findById(final UserID anUserID);


    /**
     * Verifica se um usuário existe com o email fornecido.
     *
     * <p>Este método verifica no banco de dados se há algum usuário
     * cadastrado com o email especificado.
     *
     * @param anEmail o email a ser verificado.
     * @return {@code true} se existir um usuário com o email fornecido, {@code false} caso contrário.
     */
    boolean existsByEmail(final String anEmail);


    /**
     * Busca um usuário pelo seu email.
     *
     * <p>Este método recebe um email e retorna um
     * {@link Optional} contendo o usuário correspondente, se encontrado.
     *
     * @param anEmail o email do usuário.
     * @return um {@link Optional} contendo o usuário, se encontrado; caso contrário, um Optional vazio.
     */
    Optional<User> findByEmail(final String anEmail);



}
