package org.example.springapp.contollers;

import org.example.springapp.dto.NewsDto;
import org.example.springapp.services.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@SuppressWarnings("unused")
@RestController
@RequestMapping(path = "api/news")
public class NewsController {
    private final NewsService service;

    public NewsController(NewsService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getNewsById(@PathVariable Long id) {
        NewsDto newsDto = service.getById(id);
        if (newsDto == null) {
            String response = "{\n\t\"message\": \"Новость с id " + id + " не найдена.\"\n}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(newsDto);
    }

    @GetMapping
    public Collection<NewsDto> getAllNews() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<NewsDto> createNews(@RequestBody NewsDto news) {
        NewsDto newsDto = service.create(news);
        return ResponseEntity.created(URI.create("api/news")).body(newsDto);
    }

    @PutMapping
    public ResponseEntity<NewsDto> updateNews(@RequestBody NewsDto news) {
        NewsDto newsDto = service.update(news);
        if (newsDto == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(newsDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNewsById(@PathVariable Long id) {
        if (service.deleteById(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                 .body(null);
        } else {
            String response = "{\n\t\"message\": \"Новость с id " + id + " не найдена.\"\n}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(response);
        }
    }

    @GetMapping("/category/{categoryId}")
    public Collection<NewsDto> getNewsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return service.getNewsByCategoryId(categoryId);
    }

    @GetMapping("/category")
    public Collection<NewsDto> getNewsByCategoryName(@RequestParam String name) {
        return service.getNewsByCategoryName(name);
    }

}
