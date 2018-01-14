package annuaire.repository;

import java.util.List;

import annuaire.Controler;
import org.springframework.data.repository.CrudRepository;


public interface ControllerRepository extends CrudRepository<Controler, String> {

    List<Controler> findByFlagId(String id);
}
