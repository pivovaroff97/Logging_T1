package ru.pivovarov.t1.LoggingOperations.service;

import ru.pivovarov.t1.LoggingOperations.model.User;

import java.util.List;

public interface UserService {
    User save(User user);
    List<User> getUsers();
    User getUserById(Long id);
    void deleteUserById(Long id);
}
