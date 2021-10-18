package com.sapo.ex7.service.imp;


import com.sapo.ex7.dto.InventoryDTO;
import com.sapo.ex7.dto.response.AbstractResponse;
import com.sapo.ex7.dto.response.PageResponse;
import com.sapo.ex7.entity.InventoryEntity;
import com.sapo.ex7.exception.EntityNotFoundException;
import com.sapo.ex7.repository.InventoryRepository;
import com.sapo.ex7.service.InventoryService;
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
public class InventoryServiceImpl implements InventoryService {

  private final InventoryRepository inventoryRepository;
  private final ModelMapper modelMapper;

  public InventoryServiceImpl(InventoryRepository inventoryRepository,
      ModelMapper modelMapper) {
    this.inventoryRepository = inventoryRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public InventoryDTO save(InventoryDTO dto) {
    if (dto.getId() != null) {
      InventoryEntity old = inventoryRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("not found"));
      dto.setCreatedDate(old.getCreatedDate());
    } else {
      dto.setCreatedDate(new Date(System.currentTimeMillis()));

    }
    dto.setUpdatedDate(new Date(System.currentTimeMillis()));

    InventoryEntity entity = inventoryRepository.save(modelMapper.map(dto, InventoryEntity.class));
    return modelMapper.map(entity, InventoryDTO.class);
  }

  @Override
  public ResponseEntity<AbstractResponse> delete(Long id) {
    InventoryEntity entity = inventoryRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("not found"));
    inventoryRepository.deleteById(entity.getId());

    return null;
  }


  @Override
  public AbstractResponse getById(Long id) {
    InventoryEntity entity = inventoryRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("not found"));
    return AbstractResponse.builder()
        .data(modelMapper.map(entity, InventoryDTO.class)).build();
  }

  @Override
  public Long totalItem() {
    return inventoryRepository.count();
  }



  @Override
  public AbstractResponse findAll(String key, int page, int limit) {
    Pageable pageable = PageRequest.of(page - 1, limit);

    Page<InventoryDTO> rs = inventoryRepository
        .findByNameContainsOrCodeContainsOrderByCode(key, key, pageable)
        .map(x -> modelMapper.map(x, InventoryDTO.class));

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
