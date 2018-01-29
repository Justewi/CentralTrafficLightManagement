package annuaire.repository;

import java.util.List;

import annuaire.Controler;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ControllerRepository extends MongoRepository<Controler, String> {

    List<Controler> findByFlagId(String id);
}
