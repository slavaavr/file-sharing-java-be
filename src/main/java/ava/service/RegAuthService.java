package ava.service;

import ava.db.entity.UserEntity;
import ava.db.repository.UserRepository;
import ava.error.EmailAlreadyTakenException;
import ava.error.NotValidLoginOrPasswordException;
import ava.model.request.UserLoginReq;
import ava.model.request.UserRegisterReq;
import ava.model.responce.UserLoginResp;
import ava.util.JwtTokenUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegAuthService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtTokenUtil jwtTokenUtil;

    @Transactional
    public void createUser(UserRegisterReq user) {
        if (userRepository.findOneByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyTakenException();
        } else {
            UserEntity newUser = UserEntity.builder()
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .build();
            userRepository.save(newUser);
        }

    }

    public UserLoginResp login(UserLoginReq user) {
        UserEntity entity = userRepository.findOneByEmail(user.getEmail());
        if (entity != null && passwordEncoder.matches(user.getPassword(), entity.getPassword())) {
            String token = jwtTokenUtil.generateTokenByEmail(user.getEmail());
            return UserLoginResp.builder()
                    .token(token)
                    .user(entity)
                    .build();
        } else {
            throw new NotValidLoginOrPasswordException();
        }
    }
}
