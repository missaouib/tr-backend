package com.main.app.repository.image;

import com.main.app.domain.model.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllImagesByProductId(Long productId);

    List<Image> findAllImagesByVariationId(Long variationId);

    Image findOneByProductIdAndPrimaryImageTrue(Long productId);

    Image findOneByVariationIdAndPrimaryImageTrue(Long variationId);

    Optional<Image> findOneById(Long id);


}
