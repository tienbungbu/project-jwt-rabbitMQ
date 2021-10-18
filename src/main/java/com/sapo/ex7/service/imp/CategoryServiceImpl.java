package com.sapo.ex7.service.imp;


import com.sapo.ex7.dto.CategoryDTO;
import com.sapo.ex7.dto.response.AbstractResponse;
import com.sapo.ex7.dto.response.PageResponse;
import com.sapo.ex7.entity.CategoryEntity;
import com.sapo.ex7.exception.EntityNotFoundException;
import com.sapo.ex7.repository.CategoryRepository;
import com.sapo.ex7.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final ModelMapper modelMapper;

  public CategoryServiceImpl(CategoryRepository categoryRepository,
      ModelMapper modelMapper) {
    this.categoryRepository = categoryRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public CategoryDTO save(CategoryDTO dto) {
    if (dto.getId() != null) {
      CategoryEntity old = categoryRepository.findById(dto.getId())
          .orElseThrow(() -> new EntityNotFoundException("not found"));
      dto.setCreatedDate(old.getCreatedDate());
    } else {
      dto.setCreatedDate(new Date(System.currentTimeMillis()));

    }
    dto.setUpdatedDate(new Date(System.currentTimeMillis()));
    CategoryEntity category = categoryRepository.save(modelMapper.map(dto, CategoryEntity.class));
    return modelMapper
        .map(category, CategoryDTO.class);
  }

  @Override
  public ResponseEntity<AbstractResponse> delete(Long id) {
    CategoryEntity category = categoryRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("not found"));
    categoryRepository.deleteById(category.getId());
    return null;
  }


  @Override
  public AbstractResponse getById(Long id) {
    CategoryEntity category = categoryRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("not found"));
    return AbstractResponse.builder().data(modelMapper.map(category, CategoryDTO.class)).build();
  }

  @Override
  public Long totalItem() {
    return categoryRepository.count();
  }


  @Override
  public AbstractResponse findAll(String key, int page, int limit) {
    Pageable pageable = PageRequest.of(page - 1, limit);

    Page<CategoryDTO> rs = categoryRepository
        .findByNameContainsOrCodeContainsOrderByCode(key, key, pageable)
        .map(x -> modelMapper.map(x, CategoryDTO.class));

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