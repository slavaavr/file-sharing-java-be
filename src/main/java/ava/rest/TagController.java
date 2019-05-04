package ava.rest;

import ava.service.TagService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TagController {

    TagService tagService;

    @GetMapping("/tags")
    public List<String> getTags(@RequestParam(required = false) Boolean popular) {
        if (popular) {
            return tagService.getPopularTags();
        }
        return null;
    }
}
