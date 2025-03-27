package org.example.springapp.services;

import org.example.springapp.dto.NewsDto;
import org.example.springapp.entity.News;
import org.example.springapp.mapper.NewsMapper;
import org.example.springapp.repo.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class NewsService implements CRUDService<NewsDto> {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    public NewsService(NewsRepository newsRepository, NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
    }

    @Override
    public NewsDto getById(Long id) {
        if (!newsRepository.existsById(id)) {
            return null;
        }
        return newsMapper.toDto(newsRepository.findById(id).orElseThrow());
    }

    @Override
    public Collection<NewsDto> getAll() {
        return newsRepository.findAll()
                .stream()
                .map(newsMapper::toDto)
                .toList();
    }

    @Override
    public NewsDto create(NewsDto newsDto) {
        News news = newsMapper.toEntity(newsDto);
        news = newsRepository.save(news);
        newsDto.setId(news.getId());
        return newsDto;
    }

    @Override
    public NewsDto update(NewsDto newsDto) {
        newsRepository.save(newsMapper.toEntity(newsDto));
        return newsDto;
    }

    @Override
    public boolean deleteById(Long id) {
        if (!newsRepository.existsById(id)) {
            return false;
        }
        newsRepository.deleteById(id);
        return true;
    }

    public List<NewsDto> getNewsByCategoryId(Long categoryId) {
        return newsRepository.findByCategory_Id(categoryId)
                .stream()
                .map(newsMapper::toDto)
                .toList();
    }

    public List<NewsDto> getNewsByCategoryName(String categoryName) {
        return newsRepository.findByCategory_Name(categoryName)
                .stream()
                .map(newsMapper::toDto)
                .toList();
    }
}