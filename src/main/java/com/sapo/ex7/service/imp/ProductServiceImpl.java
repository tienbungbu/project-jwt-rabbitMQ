package com.sapo.ex7.service.imp;


import com.sapo.ex7.dto.ProductDTO;
import com.sapo.ex7.dto.response.AbstractResponse;
import com.sapo.ex7.dto.response.PageResponse;
import com.sapo.ex7.entity.CategoryEntity;
import com.sapo.ex7.entity.InventoryEntity;
import com.sapo.ex7.entity.ProductEntity;
import com.sapo.ex7.exception.EntityNotFoundException;
import com.sapo.ex7.repository.CategoryRepository;
import com.sapo.ex7.repository.InventoryRepository;
import com.sapo.ex7.repository.ProductRepository;
import com.sapo.ex7.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;
  private final InventoryRepository inventoryRepository;
  private final ModelMapper modelMapper;

  public ProductServiceImpl(
      CategoryRepository categoryRepository,
      ProductRepository productRepository,
      InventoryRepository inventoryRepository,
      ModelMapper modelMapper
  ) {
    this.categoryRepository = categoryRepository;
    this.productRepository = productRepository;
    this.inventoryRepository = inventoryRepository;
    this.modelMapper = modelMapper;
  }



  @Override
  public ProductDTO save(ProductDTO entity) {
    if (entity.getId() != null) {
      ProductEntity old = productRepository.findById(entity.getId())
          .orElseThrow(() -> new EntityNotFoundException("not found"));
      entity.setCreatedDate(old.getCreatedDate());
    } else {
      entity.setCreatedDate(new Date(System.currentTimeMillis()));

    }
    entity.setUpdatedDate(new Date(System.currentTimeMillis()));
    ProductEntity product = modelMapper.map(entity, ProductEntity.class);
    CategoryEntity category = categoryRepository.findById(entity.getCategoryId())
        .orElseThrow(() -> new EntityNotFoundException("not found"));
    InventoryEntity inventory = inventoryRepository.findById(entity.getInventoryId())
        .orElseThrow(() -> new EntityNotFoundException("not found"));
    product.setCategoryEntity(category);
    product.setInventoryEntity(inventory);
    product = productRepository.save(product);
    return modelMapper.map(product, ProductDTO.class);
  }

  @Override
  public ResponseEntity<AbstractResponse> delete(Long id) {
    ProductEntity product = productRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("not found"));
    categoryRepository.deleteById(product.getId());

    return null;
  }

  @Override
  public AbstractResponse getById(Long id) {
    ProductEntity product = productRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("not found"));
    return AbstractResponse.builder().data(modelMapper.map(product, ProductDTO.class)).build();
  }

  @Override
  public Long totalItem() {
    return productRepository.count();
  }


  @Override
  public AbstractResponse findAll(String key, int page, int limit) {
    limit = limit == 0 ? 1 : limit;
    Pageable pageable = PageRequest.of(page - 1, limit);
    Page<ProductDTO> rs = productRepository
        .findByNameContainsOrCodeContainsOrderByCode(key, key, pageable)
        .map(x -> modelMapper.map(x, ProductDTO.class));

    PageResponse pageResponse = PageResponse.builder()
        .currentPage(page)
        .totalPage(rs.getTotalPages())
        .totalElements(rs.getTotalElements()).build();

    return AbstractResponse.builder()
        .data(rs.getContent())
        .meta(pageResponse)
        .build();
  }
}
