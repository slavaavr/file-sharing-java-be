package ava.db.repository;

import ava.db.entity.TagEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends CrudRepository<TagEntity, Integer> {
    @Query(value = "select title from tag order by use_counter DESC limit 5", nativeQuery = true)
    List<String> getPopularTags();

    @Modifying
    @Query(value = "insert into tag(title) select unnest(array[:titles]) on conflict do nothing", nativeQuery = true)
    void saveAllIfNotExist(@Param("titles") List<String> titles);

    List<TagEntity> findAllByTitleIsIn(List<String> titles);

}
