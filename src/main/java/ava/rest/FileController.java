package ava.rest;

import ava.db.entity.FileEntity;
import ava.dto.request.FileMeta;
import ava.dto.responce.SimpleFile;
import ava.service.FileService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    public HttpEntity<byte[]> getFile(@PathVariable String uri) {
        FileEntity file = fileService.getFile(uri);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType(file.getType()));
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + file.getTitle().replace(" ", "_"));
        header.setContentLength(file.getSize());
        return new HttpEntity<>(file.getBody(), header);
    }

    @GetMapping(value = "/files")
    public Page<SimpleFile> getPage(@RequestParam(required = false) String search,
                                    @PageableDefault(sort = {"creationDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return fileService.gePage(search, pageable);
    }
}
