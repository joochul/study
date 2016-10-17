package sample.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sample.jpa.model.BookHistory;

public interface BookHistoryRepository extends JpaRepository<BookHistory, Long> {
}