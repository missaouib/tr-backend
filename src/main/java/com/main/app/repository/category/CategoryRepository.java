package com.main.app.repository.category;

import com.main.app.domain.model.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findAllByIdIn(List<Long> idsList, Pageable pageable);

    Optional<Category> findOneByName(String name);

    Optional<Category> findOneById(Long id);

    List<Category> findAllByParentCategoryIdIsNull(Pageable pageable);

    List<Category> findAllByParentCategoryId(Long id);

    Optional<Category> findOneByParentCategoryId(Long parentId);

    List<Category> findAllByParentCategoryName(String name);
}
