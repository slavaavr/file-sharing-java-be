package ava.service;

import ava.db.entity.UserEntity;
import ava.db.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    UserRepository userRepository;

    public UserEntity getUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }
}
