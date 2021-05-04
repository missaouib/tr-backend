package com.main.app.service.image;

import com.main.app.domain.model.image.Image;

import java.util.List;


public interface ImageService {

    List<Image> getAllImagesByProductId(Long productId);

    List<Image> getAllImagesByVariationId(Long variationId);

    Image setPrimary(Long id, Long productId);

    Image setPrimaryImageForVariation(Long id, Long variationId);

    Image delete(Long id, Long productId);

    Image deleteForVariation(Long id, Long variationId);



}
