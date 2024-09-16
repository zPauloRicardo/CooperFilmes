package me.paulojr.cooperfilme.domain.user.adapters;

import me.paulojr.cooperfilme.domain.user.entity.User;

public interface TokenGenerator {

    /**
     * Gera um token de autenticação para o usuário fornecido.
     *
     * <p>Este método recebe um objeto {@link User} contendo as informações
     * do usuário e gera um token de autenticação correspondente. O token
     * pode ser usado para autenticar solicitações subsequentes do usuário.
     *
     * @param user o objeto {@link User} que contém as informações do usuário para gerar o token.
     * @return uma {@link String} representando o token de autenticação gerado.
     */
    String generateToken(User user);
}
