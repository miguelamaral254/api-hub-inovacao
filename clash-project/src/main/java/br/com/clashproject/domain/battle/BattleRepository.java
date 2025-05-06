package br.com.clashproject.domain.battle;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleRepository extends MongoRepository<Battle, String> { }