package br.com.apihubinovacao.core;

public interface CharacterDataQuery {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

}
