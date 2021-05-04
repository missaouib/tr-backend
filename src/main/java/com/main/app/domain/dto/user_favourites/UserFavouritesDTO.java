package com.main.app.domain.dto.user_favourites;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFavouritesDTO {

    private Long id;

    private Long user_id;

    private Long variation_id;

    private String name;

    private String sku;

    private String slug;

    private Double price;

    private String primaryImageUrl;


}
