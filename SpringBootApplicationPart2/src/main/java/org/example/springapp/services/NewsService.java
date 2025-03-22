package org.example.springapp.services;

import org.example.springapp.dto.NewsDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NewsService implements CRUDService<NewsDto> {
    private final Map<Long, NewsDto> newsFeed = new ConcurrentHashMap<>();

    public NewsService() {
        NewsDto o1 = new NewsDto(1L, "news_1", "Text Text Text", Instant.now());
        NewsDto o2 = new NewsDto(2L, "news_2", "Text Text Text Text", Instant.now());
        newsFeed.put(1L, o1);
        newsFeed.put(2L, o2);
    }

    @Override
    public NewsDto getById(Long id) {
        if (!newsFeed.containsKey(id)) {
            return null;
        }
        return newsFeed.get(id);
    }

    @Override
    public Collection<NewsDto> getAll() {
        return newsFeed.values();
    }

    @Override
    public NewsDto create(NewsDto item) {
        long nextId = Collections.max(newsFeed.keySet()) + 1;
        item.setId(nextId);
        if (item.getDate() == null) {
            item.setDate(Instant.now());
        }
        newsFeed.put(nextId, item);
        return item;
    }

    @Override
    public NewsDto update(NewsDto item) {
        Long id = item.getId();
        if (!newsFeed.containsKey(id)) {
            return null;
        }
        if (item.getDate() == null) {
            item.setDate(Instant.now());
        }
        newsFeed.put(id, item);
        return item;
    }

    @Override
    public boolean deleteById(Long id) {
        if (!newsFeed.containsKey(id)) {
            return false;
        }
        newsFeed.remove(id);
        return true;
    }
}