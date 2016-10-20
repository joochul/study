package sample.carMongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import sample.carMongo.entity.Car;

public interface CarMongoRepository  extends MongoRepository<Car, Long>{

}
