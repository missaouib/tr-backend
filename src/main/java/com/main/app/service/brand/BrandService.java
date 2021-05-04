package com.main.app.service.brand;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.model.brand.Brand;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * The service used for management of the Brand data.
 *
 * @author Nikola
 */
public interface BrandService {

    Entities getAll();



    Entities getAllBySearchParam(String searchParam, Pageable pageable);

    Brand getOne(Long id);

    Brand save(Brand brand);

    Brand edit(Brand brand, Long id);

    Brand delete(Long id);

    void uploadImage(Long id, MultipartFile[] images, MultipartFile[] homepageImages) throws IOException;


}
