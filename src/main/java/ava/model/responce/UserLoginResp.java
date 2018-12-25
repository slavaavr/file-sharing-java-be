package ava.model.responce;

import ava.db.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginResp {
    String token;
    UserEntity user;
}
