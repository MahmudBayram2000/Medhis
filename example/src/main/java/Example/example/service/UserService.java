package Example.example.service;

import Example.example.entity.User;
import Example.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

//    public User createUser(User user) {
//        return userRepository.save(user);
//    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void createUser(User user) {
        userRepository.createUser(user.getUsername(), user.getPassword(), user.getEmail());
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public void updateUser(Long id, User user) {
        userRepository.updateUser(id, user.getUsername(), user.getPassword(), user.getEmail());
    }



    public Optional<User> getUserByCredentials(String username, String password) {
        return userRepository.getUserByCredentials(username, password);
    }
}
