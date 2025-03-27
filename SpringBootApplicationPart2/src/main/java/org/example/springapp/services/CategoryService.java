package org.example.springapp.services;

import org.example.springapp.dto.CategoryDto;
import org.example.springapp.entity.Category;
import org.example.springapp.errors.CategoryNotFoundException;
import org.example.springapp.mapper.CategoryMapper;
import org.example.springapp.repo.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CategoryService implements CRUDService<CategoryDto> {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public Category getByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new CategoryNotFoundException(name));
    }

    @Override
    public CategoryDto getById(Long id) {
        if (!categoryRepository.existsById(id)) {
            return null;
        }
        return categoryMapper.toDto(categoryRepository.findById(id).orElseThrow());
    }

    @Override
    public Collection<CategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryRepository.save(categoryMapper.toEntity(categoryDto));
        categoryDto.setId(category.getId());
        return categoryDto;

    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        categoryRepository.save(categoryMapper.toEntity(categoryDto));
        return categoryDto;
    }

    @Override
    public boolean deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            return false;
        }
        categoryRepository.deleteById(id);
        return true;
    }
}
