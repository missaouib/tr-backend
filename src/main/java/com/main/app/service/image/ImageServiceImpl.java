package com.main.app.service.image;

import com.main.app.domain.model.image.Image;
import com.main.app.domain.model.product.Product;
import com.main.app.domain.model.variation.Variation;
import com.main.app.repository.image.ImageRepository;
import com.main.app.service.product.ProductService;
import com.main.app.service.variation.VariationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static com.main.app.static_data.Messages.IMAGE_NOT_EXIST;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final ProductService productService;

    private final VariationService variationService;


    @Override
    public List<Image> getAllImagesByProductId(Long productId) {
        return imageRepository.findAllImagesByProductId(productId);
    }

    @Override
    public List<Image> getAllImagesByVariationId(Long variationId) {
        return imageRepository.findAllImagesByVariationId(variationId);
    }

    @Override
    public Image setPrimary(Long id, Long productId) {
        Image oldPrimaryImage = imageRepository.findOneByProductIdAndPrimaryImageTrue(productId);

        if(oldPrimaryImage != null) {
            oldPrimaryImage.setPrimaryImage(false);
            imageRepository.save(oldPrimaryImage);
        }

        Image newPrimaryImage = imageRepository.getOne(id);
        newPrimaryImage.setPrimaryImage(true);
        imageRepository.save(newPrimaryImage);

        Product product = productService.getOne(productId);
        product.setPrimaryImageUrl(newPrimaryImage.getUrl());
        productService.edit(product, productId);

        return newPrimaryImage;
    }

    @Override
    public Image setPrimaryImageForVariation(Long id, Long variationId) {
        Image oldPrimaryImage = imageRepository.findOneByVariationIdAndPrimaryImageTrue(variationId);

        if(oldPrimaryImage != null) {
            oldPrimaryImage.setPrimaryImage(false);
            imageRepository.save(oldPrimaryImage);
        }

        Image newPrimaryImage = imageRepository.getOne(id);
        newPrimaryImage.setPrimaryImage(true);
        imageRepository.save(newPrimaryImage);

        Variation variation = variationService.getOne(variationId);
        variation.setPrimaryImageUrl(newPrimaryImage.getUrl());
        variationService.edit(variation, variationId);

        return newPrimaryImage;
    }


    @Override
    public Image delete(Long id, Long productId) {
        Optional<Image> optionalImage = imageRepository.findOneById(id);

        if (!optionalImage.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, IMAGE_NOT_EXIST);
        }

        Image foundImage = optionalImage.get();
        foundImage.setDeleted(true);
        foundImage.setDateDeleted(Calendar.getInstance().toInstant());

        if(foundImage.isPrimaryImage()){
            Product product = productService.getOne(productId);
            product.setPrimaryImageUrl("");
            productService.edit(product, productId);
        }

        return imageRepository.save(foundImage);
    }

    @Override
    public Image deleteForVariation(Long id, Long variationId) {
        Optional<Image> optionalImage = imageRepository.findOneById(id);

        if (!optionalImage.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, IMAGE_NOT_EXIST);
        }

        Image foundImage = optionalImage.get();
        foundImage.setDeleted(true);
        foundImage.setDateDeleted(Calendar.getInstance().toInstant());

        if(foundImage.isPrimaryImage()){
            Variation variation = variationService.getOne(variationId);
            variation.setPrimaryImageUrl("");
            variationService.edit(variation, variationId);
        }

        return imageRepository.save(foundImage);
    }


}
