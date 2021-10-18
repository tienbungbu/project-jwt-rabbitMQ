package com.sapo.ex7.repository;

import com.sapo.ex7.entity.InventoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

  @Query(value = "SELECT * FROM inventory  WHERE id = ?1", nativeQuery = true)
  InventoryEntity findOneId(Long id);

  Page<InventoryEntity> findByNameContainsOrCodeContainsOrderByCode(String name, String code,
                                                              Pageable pageable);


}
