package sample.carJpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sample.carJpa.entity.Owner;

public interface OwnerJpaRepository  extends JpaRepository<Owner, Long>{

}
