package sample.exampleCar.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import sample.exampleCar.entity.Car;

public interface ExRepository  extends MongoRepository<Car, Long>{

}
