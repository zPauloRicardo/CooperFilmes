package me.paulojr.cooperfilme.domain.script.core.gateway;

import me.paulojr.cooperfilme.domain.script.core.entity.Script;
import me.paulojr.cooperfilme.domain.script.core.entity.ScriptID;
import me.paulojr.cooperfilme.domain.script.core.search.ScriptSearchCommand;
import me.paulojr.cooperfilme.domain.script.customer.entity.CustomerID;
import me.paulojr.cooperfilme.domain.shared.search.Pagination;

import java.util.Optional;

public interface ScriptGateway {


    /**
     * Realiza uma pesquisa de scripts de acordo com os critérios especificados.
     *
     * <p>Este método recebe uma consulta ({@link ScriptSearchCommand})
     * e retorna uma página de resultados contendo objetos {@link Script} que correspondem aos
     * critérios da pesquisa.
     *
     * @param sq a consulta contendo os critérios de busca.
     * @return uma {@link Pagination} contendo os resultados da pesquisa de scripts.
     */
    Pagination<Script> search(final ScriptSearchCommand sq);


    /**
     * Busca um script pelo seu identificador único.
     *
     * <p>Este método recebe um identificador de script ({@link ScriptID}) e retorna
     * um {@link Optional} contendo o script correspondente, se encontrado.
     *
     * @param id o identificador único do script.
     * @return um {@link Optional} contendo o script, se encontrado; caso contrário, um Optional vazio.
     */
    Optional<Script> findById(ScriptID id);


    /**
     * Cria um novo script no sistema.
     *
     * <p>Este método recebe um objeto {@link Script} contendo as informações do script
     * a ser salvo e persiste esses dados no banco de dados.
     *
     * @param toSave o objeto {@link Script} contendo as informações do script a ser criado.
     * @return o objeto {@link Script} após ser salvo, contendo o ID e outras informações geradas.
     */
    Script create(final Script toSave);


    /**
     * Atualiza um script existente no sistema.
     *
     * <p>Este método recebe um objeto {@link Script} com as informações atualizadas e realiza
     * a atualização correspondente no banco de dados.
     *
     * @param toUpdate o objeto {@link Script} contendo as informações atualizadas do script.
     * @return o objeto {@link Script} após ser atualizado.
     */
    Script update(final Script toUpdate);



    /**
     * Encontra e retorna uma paginação de scripts associados a um cliente específico.
     *
     * @param idCustomer Identificador do cliente para o qual os roteiros devem ser encontrados.
     * @param page Indica o número da pagina a ser retornada
     * @return Uma instância de {@link Pagination} contendo uma lista de roteiros ({@link Script}) associados ao cliente especificado.
     * A paginação inclui informações sobre a página atual, o total de páginas e o total de itens.
     */
    Pagination<Script> findByCustomerId(CustomerID idCustomer, Integer page);

}
