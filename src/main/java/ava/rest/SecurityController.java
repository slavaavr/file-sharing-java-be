package ava.rest;

import ava.common.Methods;
import ava.model.request.UserLoginReq;
import ava.model.request.UserRegisterReq;
import ava.model.responce.UserLoginResp;
import ava.service.RegAuthService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityController {

    RegAuthService regAuthService;

    @PostMapping(Methods.REGISTER)
    public void register(@RequestBody UserRegisterReq user) {
        regAuthService.createUser(user);
    }

    @PostMapping(Methods.LOGIN)
    public UserLoginResp login(@RequestBody UserLoginReq user) {
        return regAuthService.login(user);
    }
}
