package me.jazzy.obp.service;

import lombok.AllArgsConstructor;
import me.jazzy.obp.dto.ResponseBody;
import me.jazzy.obp.model.Category;
import me.jazzy.obp.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getByName(String name) {
        return categoryRepository.findByName(name)
                    .orElseThrow(() -> new RuntimeException("There is no such category."));
    }

    public ResponseBody save(Category category) {
        categoryRepository.save(category);

        return new ResponseBody(
                HttpStatus.CREATED.value(),
                "New Category Added.",
                LocalDateTime.now()
        );
    }

    public ResponseBody update(Category category) {
        save(category);

        return new ResponseBody(
                HttpStatus.OK.value(),
                "Category Updated.",
                LocalDateTime.now()
        );
    }
}