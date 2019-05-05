package ava.db.repository;

import ava.db.entity.FileEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FileRepository extends PagingAndSortingRepository<FileEntity, Integer> {
    FileEntity findByUri(String uri);
}
