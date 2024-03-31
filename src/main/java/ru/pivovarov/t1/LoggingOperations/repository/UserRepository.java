package ru.pivovarov.t1.LoggingOperations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pivovarov.t1.LoggingOperations.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
