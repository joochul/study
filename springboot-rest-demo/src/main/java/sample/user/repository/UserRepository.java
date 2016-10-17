package sample.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sample.user.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}