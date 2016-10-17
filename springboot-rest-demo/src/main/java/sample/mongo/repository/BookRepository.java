package sample.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import sample.mongo.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}