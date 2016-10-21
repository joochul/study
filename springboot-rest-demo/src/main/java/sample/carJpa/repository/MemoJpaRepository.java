package sample.carJpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sample.carJpa.entity.Memo;

public interface MemoJpaRepository  extends JpaRepository<Memo, Long>{

}
