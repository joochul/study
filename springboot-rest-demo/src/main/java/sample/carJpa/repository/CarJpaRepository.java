package sample.carJpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sample.carJpa.entity.Car;

public interface CarJpaRepository  extends JpaRepository<Car, Long>{

}
