package ava.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Not valid login or password")
public class NotValidLoginOrPasswordException extends RuntimeException {
}
