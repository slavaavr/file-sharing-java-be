package ava.db.repository;

import ava.db.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    UserEntity findOneByEmail(@NotNull String email);
}
