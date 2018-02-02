package ctlms.repository;

import java.util.List;

import ctlms.model.City;
import ctlms.model.Controler;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends MongoRepository<City, String> {

    List<City> findByName(String name);


}