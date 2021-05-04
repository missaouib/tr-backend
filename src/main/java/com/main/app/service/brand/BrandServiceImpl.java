package com.main.app.service.brand;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.model.brand.Brand;
import com.main.app.elastic.dto.brand.BrandElasticDTO;
import com.main.app.elastic.repository.brand.BrandElasticRepository;
import com.main.app.elastic.repository.brand.BrandElasticRepositoryBuilder;
import com.main.app.repository.brand.BrandRepository;
import com.main.app.service.product.ProductService;
import com.main.app.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static com.main.app.converter.brand.BrandConverter.listToDTOList;
//import static com.main.app.converter.brand.BrandConverter.listToDTOListWithoutIds;
import static com.main.app.static_data.Messages.*;
import static com.main.app.util.MD5HashUtil.md5;
import static com.main.app.util.Util.brandsToIds;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BrandServiceImpl implements BrandService {

    @Value("${document.productcategory.path}")
    private String documentPath;

    private final BrandRepository brandRepository;

    private final BrandElasticRepository brandElasticRepository;

    private final BrandElasticRepositoryBuilder brandElasticRepositoryBuilder;

    private final ProductService productService;

    @Override
    public Entities getAll() {
        List<Brand> brandList = brandRepository.findAll();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(brandList));
        entities.setTotal(brandList.size());

        return entities;
    }



    @Override
    public Entities getAllBySearchParam(String searchParam, Pageable pageable) {
        Page<BrandElasticDTO> pagedBrands = brandElasticRepository.search(brandElasticRepositoryBuilder.generateQuery(searchParam), pageable);

        List<Long> ids = brandsToIds(pagedBrands);

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<Brand> brands = brandRepository.findAllByIdIn(ids, mySqlPaging).getContent();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(brands));
        entities.setTotal(pagedBrands.getTotalElements());

        return entities;
    }

    @Override
    public Brand getOne(Long id) {
        return brandRepository.getOne(id);
    }


    @Override
    public Brand save(Brand brand) {
        Optional<Brand> oneBrand = brandRepository.findOneByName(brand.getName());

        if(oneBrand.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BRAND_WITH_NAME_ALREADY_EXIST);
        }

        if(brand.getName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BRAND_NAME_CANT_BE_NULL);
        }

        Brand savedBrand = brandRepository.save(brand);
        brandElasticRepository.save(new BrandElasticDTO(savedBrand));

        return savedBrand;
    }

    @Override
    public Brand edit(Brand brand, Long id) {
        Optional<Brand> oneBrand = brandRepository.findOneByName(brand.getName());

        if(oneBrand.isPresent() && !id.equals(oneBrand.get().getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BRAND_WITH_NAME_ALREADY_EXIST);
        }

        Optional<Brand> optionalBrand = brandRepository.findOneById(id);

        if(!optionalBrand.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BRAND_NOT_EXIST);
        }

        Brand foundBrand = optionalBrand.get();

        if(brand.getName() != null){
            foundBrand.setName(brand.getName());
        }

        foundBrand.setDescription(brand.getDescription());

        Brand savedBrand = brandRepository.save(foundBrand);
        brandElasticRepository.save(new BrandElasticDTO(savedBrand));

        return savedBrand;
    }

    @Override
    public Brand delete(Long id) {
        Optional<Brand> optionalBrand = brandRepository.findOneById(id);

        if (!optionalBrand.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BRAND_NOT_EXIST);
        }

        productService.checkIfHasForeignKey(id, "brand");

        Brand foundBrand = optionalBrand.get();
        foundBrand.setDeleted(true);
        foundBrand.setDateDeleted(Calendar.getInstance().toInstant());

        Brand savedBrand = brandRepository.save(foundBrand);
        brandElasticRepository.save(ObjectMapperUtils.map(foundBrand, BrandElasticDTO.class));

        return savedBrand;
    }

    @Override
    public void uploadImage(Long id, MultipartFile[] images, MultipartFile[] homepageImages) throws IOException {
        Brand brand = this.getOne(id);

        for(MultipartFile uploadedFile : images) {
            String folderName = this.createDirectory().split("/")[1];
            String imageName = md5(uploadedFile.getOriginalFilename());

            byte[] bytes = uploadedFile.getBytes();

            Path path = Paths.get(documentPath + folderName + "/" + imageName  + ".png");
            Files.write(path, bytes);

            brand.setPrimaryImageUrl("images/" + folderName + "/" + imageName + ".png");
        }

        for(MultipartFile uploadedFile : homepageImages) {
            String folderName = this.createDirectory().split("/")[1];
            String imageName = md5(uploadedFile.getOriginalFilename());

            byte[] bytes = uploadedFile.getBytes();

            Path path = Paths.get(documentPath + folderName + "/" + imageName  + ".png");
            Files.write(path, bytes);

            brand.setHomepageImageUrl("images/" + folderName + "/" + imageName + ".png");

        }

        this.edit(brand, id);
    }


    private String createDirectory() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH)+1;
        String folderName = "/" + month + "-" + year;

        new File(documentPath + folderName).mkdirs();

        return folderName;
    }

}
