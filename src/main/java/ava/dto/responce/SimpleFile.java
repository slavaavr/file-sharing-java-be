package ava.dto.responce;

import ava.db.entity.TagEntity;
import ava.db.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface SimpleFile {
    Integer getId();

    UserEntity getUser();

    String getUri();

    String getTitle();

    String getType();

    Long getSize();

    String getPrettySize();

    LocalDateTime getCreationDate();

    Integer getCountDownload();

    List<TagEntity> getTags();
}
