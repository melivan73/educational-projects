package org.example.springapp.mapper;

import org.example.springapp.dto.NewsDto;
import org.example.springapp.entity.Category;
import org.example.springapp.entity.News;
import org.example.springapp.services.CategoryService;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class NewsMapper {
    private final CategoryService categoryService;

    public NewsMapper(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public News toEntity(NewsDto dto) {
        News news = new News();
        if (dto.getId() != null) {
            news.setId(dto.getId());
        }
        news.setTitle(dto.getTitle());
        news.setText(dto.getText());
        if (dto.getDate() == null) {
            dto.setDate(Instant.now());
        }
        news.setDate(dto.getDate());
        Category category = categoryService.getByName(dto.getCategory());
        news.setCategory(category);
        return news;
    }

    public NewsDto toDto(News news) {
        NewsDto dto = new NewsDto();
        dto.setId(news.getId());
        dto.setTitle(news.getTitle());
        dto.setText(news.getText());
        dto.setDate(news.getDate());
        dto.setCategory(news.getCategory().getName());
        return dto;
    }

}
