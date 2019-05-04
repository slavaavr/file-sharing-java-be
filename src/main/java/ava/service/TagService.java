package ava.service;

import ava.db.entity.TagEntity;
import ava.db.repository.TagRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TagService {

    TagRepository tagRepository;

    public List<String> getPopularTags() {
        return tagRepository.getPopularTags();
    }

    @Transactional
    public List<TagEntity> createTags(List<String> tags) {
        if (tags.size() == 0) {
            return new ArrayList<>();
        }
        for (String tag : tags) {
            tagRepository.saveIfNotExist(tag);
        }
        return tagRepository.findAllByTitleIsIn(tags);
    }
}
