package br.com.apihubinovacao.core;

public interface GenericDataQuery {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

}
