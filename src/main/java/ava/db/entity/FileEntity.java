package ava.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "file")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;
    String uri;
    @JsonIgnore
    byte[] body;
    String title;
    String type;
    Long size;

    @JsonIgnore
    String storageTime;

    Timestamp creationDate;
    Integer countDownload;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "file_tag",
            joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    List<TagEntity> tags;
}
