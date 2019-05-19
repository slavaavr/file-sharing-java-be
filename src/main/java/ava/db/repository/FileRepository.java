package ava.db.repository;

import ava.db.entity.FileEntity;
import ava.dto.responce.SimpleFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FileRepository extends CrudRepository<FileEntity, Integer> {

    FileEntity findByUri(String uri);

    @Modifying
    @Query(value = "update file set count_download=count_download+1 where id=:id", nativeQuery = true)
    void incrDownloadCounter(@Param("id") Integer id);

    @Query(value = "select * from file where title ILIKE CONCAT('%',:title,'%')", nativeQuery = true)
    Page<SimpleFile> searchFiles(@Param("title") String title, Pageable pageable);

    @Modifying
    @Query(value = "call erase_old_files()", nativeQuery = true)
    void eraseOldFiles();

    Page<SimpleFile> findAll(Pageable pageable);
}
