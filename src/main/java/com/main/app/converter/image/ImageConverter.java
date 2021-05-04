package com.main.app.converter.image;

import com.main.app.domain.dto.image.ImageDTO;
import com.main.app.domain.model.image.Image;
import com.main.app.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ImageConverter {

    private static ProductService productService;

    @Autowired
    private ImageConverter(ProductService productService) {
        ImageConverter.productService = productService;
    }

    public static Image DTOtoEntity(ImageDTO imageDTO){
        return Image
                .builder()
                .name(imageDTO.getName())
                .url(imageDTO.getUrl())
                .primaryImage(imageDTO.isPrimaryImage())
                .product(imageDTO.getProductId() != null ? productService.getOne(imageDTO.getProductId()) : null)
                .variation(null)
                .build();
    }

    public static ImageDTO entityToDTO(Image image) {
        return ImageDTO
                .builder()
                .id(image.getId())
                .name(image.getName())
                .productId(image.getProduct() != null ? image.getProduct().getId() : null)
                .variationId(image.getVariation() != null ? image.getVariation().getId() : null)
                .url(image.getUrl())
                .primaryImage(image.isPrimaryImage())
                .dateCreated(image.getDateCreated())
                .build();
    }

    public static List<ImageDTO> listToDTOList(List<Image> images) {
        return images
                .stream()
                .map(image -> entityToDTO(image))
                .collect(Collectors.toList());
    }


}
