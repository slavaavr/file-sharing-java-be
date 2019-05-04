package ava.service;

import ava.config.security.Principal;
import ava.db.entity.FileEntity;
import ava.db.entity.TagEntity;
import ava.db.repository.FileRepository;
import ava.dto.request.FileMeta;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.postgresql.util.PGInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FileService {

    FileRepository fileRepository;
    TagService tagService;

    @Transactional
    public String saveFile(FileMeta meta, MultipartFile file) throws IOException, SQLException {
        List<TagEntity> createdTags = tagService.createTags(meta.getTags());
        String uri = UUID.randomUUID().toString();
        PGInterval time = new PGInterval(meta.getTime());
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.length() > 60) {
            fileName = fileName.substring(0, 60);
        }
        FileEntity fileEntity = FileEntity.builder()
                .title(fileName)
                .type(file.getContentType())
                .body(file.getBytes())
                .size(file.getSize())
                .tags(createdTags)
                .uri(uri)
                .storageTime(time.toString())
                .countDownload(0)
                .creationDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.isAuth()) {
            fileEntity.setUser(principal.getUser());
        }
        fileRepository.save(fileEntity);
        return "http://localhost:8080/files/" + uri;
    }

    public FileEntity getFile(String uri) {
        return fileRepository.findByUri(uri);
    }
}
