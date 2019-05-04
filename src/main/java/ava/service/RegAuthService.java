package ava.service;

import ava.db.entity.UserEntity;
import ava.dto.request.UserLoginReq;
import ava.dto.request.UserRegisterReq;
import ava.dto.responce.UserLoginResp;
import ava.error.EmailAlreadyTakenException;
import ava.error.NotValidLoginOrPasswordException;
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

    UserService userService;
    PasswordEncoder passwordEncoder;
    JwtTokenUtil jwtTokenUtil;

    @Transactional
    public void createUser(UserRegisterReq user) {
        if (userService.getUserByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyTakenException();
        } else {
            UserEntity newUser = UserEntity.builder()
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .build();
            userService.createUser(newUser);
        }

    }

    public UserLoginResp login(UserLoginReq user) {
        UserEntity entity = userService.getUserByEmail(user.getEmail());
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
