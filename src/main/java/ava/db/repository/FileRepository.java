package ava.db.repository;

import ava.db.entity.FileEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface FileRepository extends PagingAndSortingRepository<FileEntity, Integer> {
    FileEntity findByUri(String uri);

    @Modifying
    @Query(value = "update file set count_download=count_download+1 where id=:id", nativeQuery = true)
    void incrDownloadCounter(@Param("id") Integer id);
}
