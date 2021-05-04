package com.main.app.service.variation;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.product.ProductDTO;
import com.main.app.domain.model.variation.Variation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VariationService {

    Entities getAll();

    Entities getAllBySearchParam(String searchParam, String productId, Pageable pageable);

    Variation getOne(Long id);

    boolean save(ProductDTO productDTO, Long productId);

    Variation edit(Variation product, Long id);



    void uploadImage(Long id, MultipartFile[] images) throws IOException;

    Variation toggleActivate(Long id);



    List<String> getAllAttributeNamesById(Long id);

    List<String> getAllAttributeValueNamesById(Long id);


    Entities findAllForProductId(Long product_id);

    Variation getVariationByAttributeValueIdCombination(List<String> searchParam, String productId);
}
