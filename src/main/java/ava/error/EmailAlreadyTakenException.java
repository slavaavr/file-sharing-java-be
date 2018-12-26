package ava.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "User with such email already exist")
public class EmailAlreadyTakenException extends RuntimeException{
}
