package org.example.springapp.contollers;

import org.example.springapp.dto.CategoryDto;
import org.example.springapp.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@SuppressWarnings("unused")
@RestController
@RequestMapping(path = "api/categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = service.getById(id);
        if (categoryDto == null) {
            String response = "{\n\t\"message\": \"Категория с id " + id + " не найдена.\"\n}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping
    public Collection<CategoryDto> getAllCategories() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto category) {
        CategoryDto categoryDto = service.create(category);
        return ResponseEntity.created(URI.create("api/categories")).body(categoryDto);
    }

    @PutMapping
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto category) {
        CategoryDto categoryDto = service.update(category);
        if (categoryDto == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
        if (service.deleteById(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                 .body(null);
        } else {
            String response = "{\n\t\"message\": \"Категория с id " + id + " не найдена.\"\n}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(response);
        }
    }

}
