package ava.rest;

import ava.db.entity.FileEntity;
import ava.dto.request.FileMeta;
import ava.service.FileService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FileController {

    FileService fileService;

    @PostMapping(value = "/files", consumes = {"multipart/form-data"})
    public String uploadFile(@RequestPart FileMeta meta, @RequestPart MultipartFile file) throws IOException, SQLException {
        return fileService.saveFile(meta, file);
    }

    @GetMapping(value = "/files/{uri}")
    public FileEntity getFile(@PathVariable String uri) {
        return fileService.getFile(uri);
    }
}
