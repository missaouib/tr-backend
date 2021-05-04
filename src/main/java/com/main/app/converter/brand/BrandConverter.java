package com.main.app.converter.brand;

import com.main.app.domain.dto.brand.BrandDTO;
import com.main.app.domain.model.brand.Brand;

import java.util.List;
import java.util.stream.Collectors;

public class BrandConverter {

    public static Brand DTOtoEntity(BrandDTO brandDTO){
        return Brand
                .builder()
                .name(brandDTO.getName())
                .description(brandDTO.getDescription())
                .primaryImageUrl(brandDTO.getPrimaryImageUrl())
                .homepageImageUrl(brandDTO.getHomepageImageUrl())
                .build();
    }

    public static BrandDTO entityToDTO(Brand brand){
        return BrandDTO
                .builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .primaryImageUrl(brand.getPrimaryImageUrl())
                .homepageImageUrl(brand.getHomepageImageUrl())
                .dateCreated(brand.getDateCreated())
                .build();
    }

    public static BrandDTO entityToSafeDTO(Brand brand){
        return BrandDTO
                .builder()
                .id(brand.getId()) //?
                .name(brand.getName())
                .description(brand.getDescription())
                .primaryImageUrl(brand.getPrimaryImageUrl())
                .homepageImageUrl(brand.getHomepageImageUrl())
                .dateCreated(brand.getDateCreated())
                .build();
    }

    public static List<BrandDTO> listToDTOList(List<Brand> brands) {
        return brands
                .stream()
                .map(brand -> entityToDTO(brand))
                .collect(Collectors.toList());
    }

    public static List<BrandDTO> listToDTOListWithoutIds(List<Brand> brands) {
        return brands
                .stream()
                .map(brand -> entityToSafeDTO(brand))
                .collect(Collectors.toList());
    }

}
