package ava.db.repository;

import ava.db.entity.FileEntity;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<FileEntity, Integer> {
    FileEntity findByUri(String uri);
}
