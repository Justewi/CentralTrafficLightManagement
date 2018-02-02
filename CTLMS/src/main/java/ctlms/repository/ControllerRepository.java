package ctlms.repository;

import java.util.List;

import ctlms.model.Controler;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControllerRepository extends MongoRepository<Controler, String> {

    List<Controler> findByFlagId(String id);
}
