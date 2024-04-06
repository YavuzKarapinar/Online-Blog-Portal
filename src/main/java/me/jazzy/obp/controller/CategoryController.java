package me.jazzy.obp.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import me.jazzy.obp.dto.ResponseBody;
import me.jazzy.obp.model.Category;
import me.jazzy.obp.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/categories")
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "obp")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> saveCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ResponseBody> updateCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.update(category), HttpStatus.CREATED);
    }
}