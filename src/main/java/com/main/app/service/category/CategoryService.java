package com.main.app.service.category;


import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.category.CategoryDTO;
import com.main.app.domain.model.category.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    Entities getAll();

    Entities getAllBySearchParam(String searchParam, Pageable pageable);

    Category getOne(Long id);

    Category save(Category category);

    Category edit(Category category, Long id);

    Category delete(Long id);

    void uploadImage(Long id, MultipartFile[] images) throws IOException;


    List<CategoryDTO> getAllWhereNameIsParentCategory(String name);

    Category findByCategoryName(String name);
}
