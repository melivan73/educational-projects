package org.example.springapp.repo;

import org.example.springapp.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByCategory_Id(Long categoryId);
    List<News> findByCategory_Name(String categoryName);
}
